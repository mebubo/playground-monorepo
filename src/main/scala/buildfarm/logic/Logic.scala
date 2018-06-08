package buildfarm.logic

import java.time.Instant
import java.time.temporal.ChronoUnit

import buildfarm.algebra.{Drone, MachineNode, Machines}
import scalaz._
import Scalaz._

import scala.concurrent.duration._

final case class WorldView(
  backlog: Int,
  agents: Int,
  managed: NonEmptyList[MachineNode],
  alive: Map[MachineNode, Instant],
  pending: Map[MachineNode, Instant],
  time: Instant
)

final class DynAgents[F[_]](D: Drone[F], M: Machines[F])(implicit F: Monad[F]) {
  def initial: F[WorldView] =
    ^^^^(D.getBacklog, D.getAgents, M.getManaged, M.getAlive, M.getTime) {
      case (db, da, mm, ma, mt) => WorldView(db, da, mm, ma, Map.empty, mt)
    }

  def update(old: WorldView): F[WorldView] = for {
    snap <- initial
    changed = symdiff(old.alive.keySet, snap.alive.keySet)
    pending = (old.pending -- changed).filterNot {
      case (_, started) => timediff(started, snap.time) >= 10.minutes
    }
    update = snap.copy(pending = pending)
  } yield update

  def act(world: WorldView): F[WorldView] = world match {
    case NeedsAgent(node) => for {
        _ <- M.start(node)
      } yield world.copy(pending = Map(node -> world.time))
    case Stale(nodes) => nodes.foldLeftM(world) { (world, n) =>
      for {
        stopped <- nodes.traverse(M.stop)
        updates = stopped.map(_ -> world.time).toList.toMap
      } yield world.copy(pending = world.pending ++ updates)
      for {
        _ <- M.stop(n)
      } yield world.copy(pending = world.pending + (n -> world.time))
    }
    case _ => world.pure[F]
  }

  private def symdiff[T](a: Set[T], b: Set[T]): Set[T] =
    (a union b) -- (a intersect b)

  private def timediff(from: Instant, to: Instant): FiniteDuration =
    ChronoUnit.MINUTES.between(from, to).minutes

  private object NeedsAgent {
    def unapply(world: WorldView): Option[MachineNode] = world match {
      case WorldView(backlog, 0, managed, alive, pending, _)
        if backlog > 0 && alive.isEmpty && pending.isEmpty => Option(managed.head)
      case _ => None
    }
  }

  private object Stale {
    def unapply(world: WorldView): Option[NonEmptyList[MachineNode]] = world match {
      case WorldView(backlog, _, _, alive, pending, time) if alive.nonEmpty =>
        (alive -- pending.keys).collect {
          case (n, started) if backlog == 0 && timediff(started, time).toMinutes % 60 >= 58 => n
          case (n, started) if timediff(started, time) >= 5.hours => n
        }.toList.toNel
      case _ => None
    }
  }
}
