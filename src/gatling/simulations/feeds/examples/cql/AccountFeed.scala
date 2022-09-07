package feeds.examples.cql

import com.datastax.{CustomerAccount, DataGenerator}
import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging
import common.RingBuffer
import io.gatling.core.Predef.{configuration, csv}

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.reflect.io.File

class AccountFeed(dataGen: DataGenerator) extends BaseFeed with LazyLogging{
  private val generator = dataGen
  private var generatedAccounts = RingBuffer[CustomerAccount](1000)
  private var writtenAccountNumbers = RingBuffer[String](10000)

  private val dataFilePath = "src/main/resources/feeddata/accoutData.csv"  //todo, pull from configuration
  private var dataFile = null
  private var accountFileData = null

  def writeFakeAccount = {
    def rowData = getFakeAccount
    Iterator.continually(rowData)
  }

  def getFakeAccount = {
    val accountNumber = faker.numerify("#########")
    //store generated account for later use
    writtenAccountNumbers = writtenAccountNumbers.push(accountNumber)._2

    val newRow = Map(
      "account_number" -> accountNumber
    )
    newRow
  }

  def writeAccountStatement = {
    def rowData = getAccountStatementData
    Iterator.continually(rowData)
  }

  def getAccountStatementData = {
    val accountNumber = faker.numerify("#########")
    val acctExchange =  generator.getGeneratedAccount(accountNumber, "FX")
    val custAcct = acctExchange.getDataObject
    val bountStmt =acctExchange.getBoundStatement

    //store generated account for later use
    generatedAccounts = generatedAccounts.push(custAcct)._2

    val newRow = Map(
      "account_number" -> accountNumber,
      "opco" -> "FX",
      "statement"->bountStmt
    )
    newRow
  }

  def writeAccountFileSource = {
    csv("src/main/resources/feeddata/accoutData.csv")
      .convert{
        case("account_number", v) => {
          writtenAccountNumbers = writtenAccountNumbers.push(v)._2
          v
        }
        case (anyOtherKey, v) => v
      }
      .circular
  }

  def readAccoutnNum = {
    def rowData = getReadAccountNum
    Iterator.continually(rowData)
  }

  def getReadAccountNum = {
    val retrievedWrittenAcctEntry = writtenAccountNumbers.pop
    val writtenAcct = retrievedWrittenAcctEntry._1
    writtenAccountNumbers = retrievedWrittenAcctEntry._2

    val newReadRow = Map(
      "account_number" -> writtenAcct
    )
    newReadRow
  }


  def readAccountStatement = {
    def rowData = getAccountReadRowData
    Iterator.continually(rowData)
  }

  def getAccountReadRowData = {
    val retrievedAcctInfo = generatedAccounts.pop
    val savedAcct = retrievedAcctInfo._1
    generatedAccounts = retrievedAcctInfo._2

    val newReadRow = Map(
      "account_number" -> savedAcct.getAccountNumber,
      "opco" -> savedAcct.getOpco
    )
    newReadRow
  }

  //code to generate CSV file with fake account numbers if needed
  //
  //  def outputDataFile() = {
  //    val pw = new PrintWriter(new File("/Users/michaeldownie/accoutData.csv" ))
  //    pw.write("account_number\n")
  //    for(i <- 1 to 10000){
  //      pw.write(faker.numerify("#########") + "\n")
  //    }
  //    pw.close
  //  }


}
