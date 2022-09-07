package actions.examples.cql

import com.datastax.DataGenerator
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import com.datastax.oss.driver.api.core.ConsistencyLevel
import com.datastax.oss.driver.api.core.cql.BoundStatement
import com.datastax.oss.driver.api.querybuilder.QueryBuilder._
import io.gatling.core.Predef._

class AccountActions(dataGen: DataGenerator, cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {
  private val generator = dataGen

  private val readAccountQuery = selectFrom(keyspace, table)
    .all()
    .whereColumn("account_number").isEqualTo(bindMarker())
    .whereColumn("opco").isEqualTo(bindMarker())

  private val preparedAcctReadStatement = session.prepare(readAccountQuery.build())

  def writeAcountOpco(opco: String)= {
    group(Groups.INSERT) {
      exec(session => {
       // val acctNum = session.get("account_number").as[String]
       //  val acctNum = session.getString("account_number")
       val acctNum = "123123123"
        session.set("acctStmt", generator.getGeneratedAccount(acctNum, opco).getBoundStatement)
      })
      .exec(cql("Account")
        .executeBoundStatementParam("acctStmt")
      )
    }
  }

  def writeAcountOpcoWithCustomConsistencyLevel(opco: String, customCL: ConsistencyLevel)= {
    group(Groups.INSERT) {
      exec(session => {
       // val acctNum = session.get("account_number").as[String]
       // val acctNum = session.getString("account_number")
        
         val acctNum = "123123123"
        session.set("acctStmt",
                    generator.getGeneratedAccount(acctNum, opco)
                      .getBoundStatement
                      .setConsistencyLevel(customCL))
      })
        .exec(cql("Account-"+customCL.toString)
          .executeBoundStatementParam("acctStmt")
        )
    }
  }

  def ReadAccountOpco(opco: String) = {
    group(Groups.SELECT) {
      exec(session => {
        session.set("opco", opco)
      })
//        .exec(session =>{
//          val acct = session.get("account_number").as[String]
//          val op = session.get("opco").as[String]
//          session
//        })  //location for debugging detail if needed
      .exec(cql("Account")
        .executeStatement(preparedAcctReadStatement)
        .withParams(
          "${account_number}",
          "${opco}"
        )
        /*
        .check(resultSet.transform(_.hasMorePages) is false)
        .check(resultSet.transform(_.remaining) is 1)
        .check(resultSet.transform(rs => generator.getCustomerAccountEntity(rs.one()) != null) is true)
        */
      )
    }
  }


  def writeAccountStatement = {
    group(Groups.INSERT) {
      exec(session => {
        val stmt = session("statement").as[BoundStatement]
        session.set("acctWriteStmt", stmt)
      })
      .exec(cql("Account")
        .executeBoundStatementParam("acctWriteStmt")
      )
    }
  }

  def ReadAccount = {
    group(Groups.SELECT) {
      exec(cql("Account")
        .executeStatement(preparedAcctReadStatement)
          .withParams(
            "${account_number}",
            "${opco}"
          )
        /*  
        .check(resultSet.transform(_.hasMorePages) is false)
        .check(resultSet.transform(_.remaining) is 1)
        .check(resultSet.transform(rs => generator.getCustomerAccountEntity(rs.one()) != null) is true)
        */
      )
    }
  }
}
