package actions.examples.solr

import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.{bindMarker, selectFrom}
import io.gatling.core.Predef._

class CAMSearchActions (cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  private val readSearchQuery = selectFrom(keyspace, table)
    .all()
    .whereColumn("solr_query").isEqualTo(bindMarker())

  private val preparedReadSearchStatement = session.prepare(readSearchQuery.build())

  def ReadSearch = {
    group(Groups.SELECT) {
      exec(cql("CAMSearch")
        .executeStatement(preparedReadSearchStatement)
        .withParams(
          "${solr_query}"
        )
        .check(resultSet.transform(_.remaining) greaterThanOrEqual  1)
      )
    }
  }


}
