package sims.examples.cql.accounts

import actions.examples.cql.AccountActions
import com.datastax.DataGenerator
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.cql.AccountFeed
import io.gatling.core.Predef._

class WriteAccountSimulation extends BaseSimulation {

  val simName = "cam"
  val scenarioName = "writeAccount"
  val simConf = new SimConfig(conf, simName, scenarioName)

  protected lazy val keyspace: String = simConf.getSimulationConfStr("keyspace")
  protected lazy val table: String = simConf.getSimulationConfStr("table")
  protected lazy val dataGenerator = new DataGenerator(cass.getSession, keyspace)

  val accountActions = new AccountActions(dataGenerator, cass, simConf)

  val writeFeed = new AccountFeed(dataGenerator).writeAccountStatement

  val writeAccountScenario = scenario("AccountWrite")
    .feed(writeFeed)
    .exec(accountActions.writeAccountStatement)

  setUp(
    loadGenerator.rampUpToConstant(writeAccountScenario, simConf)
  ).protocols(cqlProtocol)
}
