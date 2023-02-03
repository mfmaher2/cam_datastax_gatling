package sims.examples.solr.accounts

import actions.examples.solr.CAMSearchActions
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.solr.CAMSearchFeed
import io.gatling.core.Predef._

class CAMSearchSimulation extends BaseSimulation {

  val simName = "camSearch"
  val scenarioName = "querySearch"
  val simConf = new SimConfig(conf, simName, scenarioName)
  val maxIDNum = simConf.getSimulationConfInt("maxID")

  val searchActions = new CAMSearchActions(cass, simConf)
  val searchFeed = new CAMSearchFeed(maxIDNum).readSearch

  val searchScenario = scenario("CAMSearch")
    .feed(searchFeed)
    .exec(searchActions.ReadSearch)

  setUp(
    loadGenerator.rampUpToConstant(searchScenario, simConf)
  ).protocols(cqlProtocol)
}
