package sims.examples.cql.accounts

import actions.examples.cql.AccountActions
import com.datastax.DataGenerator
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.cql.AccountFeed
import io.gatling.core.Predef._

import scala.concurrent.duration.{Duration, DurationInt, FiniteDuration}


class ReadWriteAccountSimulation  extends BaseSimulation {

  val simName = "cam"
  val scenarioName = "writeAccount"
  val simConf = new SimConfig(conf, simName, scenarioName)

  protected lazy val keyspace: String = simConf.getSimulationConfStr("keyspace")
  protected lazy val table: String = simConf.getSimulationConfStr("table")
  protected lazy val dataGenerator = new DataGenerator(cass.getSession, keyspace)

  val accountActions = new AccountActions(dataGenerator, cass, simConf)


  val acctFeed = new AccountFeed(dataGenerator)
  val writeFeed = acctFeed.writeAccountStatement
  val readFeed = acctFeed.readAccountStatement

  val writeAccountScenario = scenario("AccountWrite")
    .feed(writeFeed)
    .exec(accountActions.writeAccountStatement)

  val readAccountScenario = scenario("AccountRead")
    .feed(readFeed)
    .exec(accountActions.ReadAccount)

  //gather values from simulation config file
  val userCnt = simConf.getSimulationConfInt("usersConstantCnt")
  val rampTime = Duration(simConf.getSimulationConfStr("usersRampTime")).asInstanceOf[FiniteDuration]
  val constantTime =  Duration(simConf.getSimulationConfStr("usersConstantTime")).asInstanceOf[FiniteDuration]

  setUp(
    //run test with a 2 to 1 write to read ratio
    writeAccountScenario.inject(
      rampUsersPerSec(1) to userCnt*2 during rampTime,
      constantUsersPerSec(userCnt*2) during constantTime
    ),

    readAccountScenario.inject(
      nothingFor(5.seconds),
      rampUsersPerSec(1) to userCnt during rampTime,
      constantUsersPerSec(userCnt) during constantTime
    )
  ).protocols(cqlProtocol)
}
