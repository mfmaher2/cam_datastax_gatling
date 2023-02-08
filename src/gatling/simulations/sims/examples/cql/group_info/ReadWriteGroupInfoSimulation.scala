package sims.examples.cql.group_info

import actions.examples.cql.GroupInfoActions
import com.datastax.DataGenerator
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.cql.GroupInfoFeed
import io.gatling.core.Predef._

import scala.concurrent.duration.{Duration, DurationInt, FiniteDuration}


class ReadWriteGroupInfoSimulation  extends BaseSimulation {

  val simName = "cam"
  val scenarioName = "writeGroupInfo"
  val simConf = new SimConfig(conf, simName, scenarioName)

  protected lazy val keyspace: String = simConf.getSimulationConfStr("keyspace")
  protected lazy val table: String = simConf.getSimulationConfStr("table")
  protected lazy val dataGenerator = new DataGenerator(cass.getSession, keyspace)

  val grpInfoActions = new GroupInfoActions(dataGenerator, cass, simConf)


  val grpInfoFeed = new GroupInfoFeed(dataGenerator)
  val writeFeed = grpInfoFeed.writeAccountStatement
  val readFeed = grpInfoFeed.readAccountStatement

  val writeGroupInfoScenario = scenario("GroupInfoWrite")
    .feed(writeFeed)
    .exec(grpInfoActions.writeAccountStatement)

  val readGroupInfoScenario = scenario("GroupInfoRead")
    .feed(readFeed)
    .exec(grpInfoActions.ReadAccount)

  //gather values from simulation config file
  val userCnt = simConf.getSimulationConfInt("usersConstantCnt")
  println("userCount:"+ userCnt)
  val rampTime = Duration(simConf.getSimulationConfStr("usersRampTime")).asInstanceOf[FiniteDuration]
  val constantTime =  Duration(simConf.getSimulationConfStr("usersConstantTime")).asInstanceOf[FiniteDuration]

  setUp(
    //run test with a 2 to 1 write to read ratio
    writeGroupInfoScenario.inject(
      rampUsersPerSec(1) to userCnt*2 during rampTime,
      constantUsersPerSec(userCnt*2) during constantTime
    ),

    readGroupInfoScenario.inject(
      nothingFor(5.seconds),
      rampUsersPerSec(1) to userCnt during rampTime,
      constantUsersPerSec(userCnt) during constantTime
    )
  ).protocols(cqlProtocol)
}
