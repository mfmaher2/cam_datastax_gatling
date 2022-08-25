package actions.examples.cql

//import com.datastax.driver.core.ConsistencyLevel
//import com.datastax.driver.core.querybuilder.QueryBuilder
//import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import com.datastax.oss.driver.api.querybuilder.QueryBuilder._


/**
  * Order Actions
  *
  * @param cassandra Cassandra
  * @param simConf   SimConf
  */
class OrderActions(cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  // create keyspace/table if they do not exist
  createKeyspace
  createTables()

  // A regular string query can be used as well as the QueryBuilder
  private val writeOrderQuery = insertInto(keyspace, table)
      .value("order_no", bindMarker())
      .value("alt_fname", bindMarker())
      .value("alt_lname", bindMarker())
      .value("city", bindMarker())
      .value("cust_id", bindMarker())
      .value("data", bindMarker())
//      .value("email", bindMarker())
//      .value("esd", bindMarker())
//      .value("fname", bindMarker())
//      .value("fulfill_type", bindMarker())
//      .value("group_no", bindMarker())
//      .value("hold_status", bindMarker())
//      .value("hold_type", bindMarker())
//      .value("item_id", bindMarker())
//      .value("line_code", bindMarker())
//      .value("line_status", bindMarker())
//      .value("lname", bindMarker())
//      .value("modified_dt", bindMarker())
//      .value("offer_id", bindMarker())
//      .value("opd", bindMarker())
//      .value("order_date", bindMarker())
//      .value("order_type", bindMarker())
//      .value("pallet_asn", bindMarker())
//      .value("partner_item_id", bindMarker())
//      .value("phone", bindMarker())
//      .value("pi_hash", bindMarker())
//      .value("pkg_asn", bindMarker())
//      .value("po_line_code", bindMarker())
//      .value("po_line_status", bindMarker())
//      .value("po_no", bindMarker())
//      .value("rma", bindMarker())
//      .value("seller_id", bindMarker())
//      .value("shard_id", bindMarker())
//      .value("ship_method", bindMarker())
//      .value("ship_node", bindMarker())
//      .value("source", bindMarker())
//      .value("state", bindMarker())
//      .value("store_id", bindMarker())
//      .value("store_tc_no", bindMarker())
//      .value("tc_no", bindMarker())
//      .value("tracking_no", bindMarker())
//      .value("upc", bindMarker())


  def writeOrder = {

    val preparedStatement = session.prepare(writeOrderQuery.build())

    group(Groups.INSERT) {
      exec(cql("Order")
        .executeStatement(preparedStatement)
//          .executePrepared(preparedStatement)
          .withParams(
            "${order_no}",
            "${alt_fname}",
            "${alt_lname}",
            "${city}",
            "${cust_id}",
            "${data}" //,
//            "${email}",
//            "${esd}",
//            "${fname}",
//            "${fulfill_type}",
//            "${group_no}",
//            "${hold_status}",
//            "${hold_type}",
//            "${item_id}",
//            "${line_code}",
//            "${line_status}",
//            "${lname}",
//            "${modified_dt}",
//            "${offer_id}",
//            "${opd}",
//            "${order_date}",
//            "${order_type}",
//            "${pallet_asn}",
//            "${partner_item_id}",
//            "${phone}",
//            "${pi_hash}",
//            "${pkg_asn}",
//            "${po_line_code}",
//            "${po_line_status}",
//            "${po_no}",
//            "${rma}",
//            "${seller_id}",
//            "${shard_id}",
//            "${ship_method}",
//            "${ship_node}",
//            "${source}",
//            "${state}",
//            "${store_id}",
//            "${store_tc_no}",
//            "${tc_no}",
//            "${tracking_no}",
//            "${upc}"
          )
//          .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
//          .check(rowCount is 0) // an insert should not return rows
      )
    }
  }


//  def writeOrderWithLwt = {
//
//    val query = writeOrderQuery.ifNotExists()
//    val preparedStatement2 = session.prepare(query.build())
//
//    group(Groups.INSERT) {
//      exec(cql("OrderLwt")
//          .executePrepared(preparedStatement2)
//          .withParams(
//            "${order_no}",
//            "${alt_fname}",
//            "${alt_lname}",
//            "${city}",
//            "${cust_id}",
//            "${data}",
//            "${email}",
//            "${esd}",
//            "${fname}",
//            "${fulfill_type}",
//            "${group_no}",
//            "${hold_status}",
//            "${hold_type}",
//            "${item_id}",
//            "${line_code}",
//            "${line_status}",
//            "${lname}",
//            "${modified_dt}",
//            "${offer_id}",
//            "${opd}",
//            "${order_date}",
//            "${order_type}",
//            "${pallet_asn}",
//            "${partner_item_id}",
//            "${phone}",
//            "${pi_hash}",
//            "${pkg_asn}",
//            "${po_line_code}",
//            "${po_line_status}",
//            "${po_no}",
//            "${rma}",
//            "${seller_id}",
//            "${shard_id}",
//            "${ship_method}",
//            "${ship_node}",
//            "${source}",
//            "${state}",
//            "${store_id}",
//            "${store_tc_no}",
//            "${tc_no}",
//            "${tracking_no}",
//            "${upc}"
//          )
////          .consistencyLevel(ConsistencyLevel.LOCAL_SERIAL) // Consitency of LWT can also be set //todo - update
////          .check(columnValue("[applied]") is true) // since this uses LWT we want to make sure that it succeeded
//      )
//    }
//  }


//  val readOrderQuery = QueryBuilder.select().from(keyspace, table).where(QueryBuilder.eq("order_no", raw("?")))
//  val readOrderQuery =
//      selectFrom(keyspace, table)
//        .all()
//        .whereColumn("order_no").isEqualTo(raw("?"))
//
//  def readOrder = {
//
//    val preparedStatement = session.prepare(readOrderQuery.build())
//
//    group(Groups.SELECT) {
//      exec(cql("Order")
//          .executePrepared(preparedStatement)
//          .withParams(
//            "${order_no}"
//          )
////          .check(rowCount greaterThan 0) //todo - upadte
//      )
//    }
//  }


//  def readOrderAndSaveToSession = {
//
//    val preparedStatement = session.prepare(readOrderQuery.build())
//
//    group(Groups.SELECT) {
//      exec(cql("Order2")
//          .executePrepared(preparedStatement)
//          .withParams(
//            "${order_no}"
//          )
////          .check(rowCount greaterThan 0)
//
//          // This will save the value of col "email" to a "testParam" in the current session
//          // This can be used like a feed in the withParams("$testParam") for reuse
////          .check(columnValue("email").find.saveAs("testParam"))
//
//      ).exitHereIfFailed // Print out for debugging
//          .exec { session =>
//        println(session)
//        session
//      }
//    }
//  }
//

  def createTables(): Unit = {

    runQueries(Array(

      s"CREATE TABLE IF NOT EXISTS $keyspace.$table ( order_no text, alt_fname text, alt_lname text, " +
          s"city text, cust_id text, data text, email text, esd set<timestamp>, " +
          s"fname text, fulfill_type text, group_no text, hold_status text, hold_type text, item_id text, line_code text, " +
          s"line_status text, lname text, modified_dt timestamp, offer_id text, opd set<timestamp>, order_date timestamp, " +
          s"order_type text, pallet_asn text, partner_item_id text, phone text, pi_hash text, pkg_asn text, " +
          s"po_line_code text, po_line_status text, po_no text, rma set<text>, seller_id text, shard_id text, ship_method text, " +
          s"ship_node text, source text, state text, store_id text, store_tc_no text, tc_no text, " +
          s"tracking_no text, upc text, PRIMARY KEY (order_no))"

    ))

  }

}
