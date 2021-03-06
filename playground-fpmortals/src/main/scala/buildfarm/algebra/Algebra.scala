package buildfarm.algebra

import java.time.Instant
import scalaz.NonEmptyList

trait Drone[F[_]] {
  def getBacklog: F[Int]
  def getAgents: F[Int]
}

final case class MachineNode(id: String)

trait Machines[F[_]] {
  def getTime: F[Instant]
  def getManaged: F[NonEmptyList[MachineNode]]
  def getAlive: F[Map[MachineNode, Instant]]
  def start(node: MachineNode): F[Unit]
  def stop(node: MachineNode): F[Unit]
}

