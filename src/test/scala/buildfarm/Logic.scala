package buildfarm

import java.time.Instant

import buildfarm.algebra.{Drone, MachineNode, Machines}
import buildfarm.logic.{DynAgents, WorldView}
import scalaz.NonEmptyList

object Data {
  val node1 = MachineNode("1243d1af-828f-4ba3-9fc0-a19d86852b5a")
  val node2 = MachineNode("550c4943-229e-47b0-b6be-3d686c5f013f")
  val managed = NonEmptyList(node1, node2)

  import java.time.Instant.parse

  val time1 = parse("2017-03-03T18:07:00.00Z")
  val time2 = parse("2017-03-03T18:59:00.00Z") // +52 mins
  val time3 = parse("2017-03-03T19:06:00.00Z") // +59 mins
  val time4 = parse("2017-03-03T23:07:00.00Z") // +5 hours

  val needsAgents = WorldView(5, 0, managed, Map.empty, Map.empty, time1)
}

import Data._

class Mutable(state: WorldView) {
  type Id[T] = T
  var started, stopped: Int = 0

  private val D: Drone[Id] = new Drone[Id] {
    override def getBacklog: Int = state.backlog
    override def getAgents: Int = state.agents
  }

  private val M: Machines[Id] = new Machines[Id] {
    override def getTime: Instant = state.time
    override def getManaged: NonEmptyList[MachineNode] = state.managed
    override def getAlive: Map[MachineNode, Instant] = state.alive
    override def start(node: MachineNode): MachineNode = { started += 1; node }
    override def stop(node: MachineNode): MachineNode = { stopped += 1; node }
  }

  val program = new DynAgents[Id](D, M)
}


import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class SetSpec extends FlatSpec {

  "Business Logic" should "generate an initial world view" in {
    val mutable = new Mutable(needsAgents)
    import mutable._

    program.initial shouldBe needsAgents
  }

  it should "remove changed nodes from pending" in {
    val world = WorldView(backlog = 0, agents = 0, managed = managed, alive = Map(node1 -> time3), pending = Map.empty, time = time3)
    val mutable = new Mutable(world)
    import mutable._

    val old = world.copy(alive = Map.empty,
      pending = Map(node1 -> time2)
      )
    program.update(old) shouldBe world
  }

  it should "request agents when needed" in {
    val mutable = new Mutable(needsAgents)
    import mutable._

    val expected = needsAgents.copy(
      pending = Map(node1 -> time1)
    )

    program.act(needsAgents) shouldBe expected

    mutable.stopped shouldBe 0
    mutable.started shouldBe 1
  }
}


