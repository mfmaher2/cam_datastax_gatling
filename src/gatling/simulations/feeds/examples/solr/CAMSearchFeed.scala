package feeds.examples.solr

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

import java.util.Random


class CAMSearchFeed extends BaseFeed with LazyLogging {

  val random = new Random()

    def readSearch = Iterator.continually(getSearchData)

    private def getSearchData = {
      val queryType = random.nextInt(10)
//      val queryType = 1
      val id = (random.nextInt(100000) + 1)

//      'profile__account_type:custAcctType_1001'
//      val searchString = "person__first_name:*" + (random.nextInt(100000) + 1)
//      val searchString = "profile__account_type:custAcctType_" +

      val searchString = queryType match {
        case 1 => generateSearchStringType1(id)
        case 2 => generateSearchStringType2(id)
        case _ => generateSearchStringType1(id)  //Simplest pattern of type 1 is default
      }

//      Map("solr_query" -> searchString)
      Map("solr_query" -> searchString)
    }

  def generateSearchStringType1(ID:Int) : String = {
    //Single exact match parameter
    getCustomerAccountTypeSearchString(ID)
  }

  def generateSearchStringType2(ID:Int) : String = {
    //Two exact match parameter
    getCustomerAccountTypeSearchString(ID) + " && " +
    getCustomerAccoutStatusSearchString(ID)
  }

  def getCustomerAccountTypeSearchString(ID: Int) : String = {
    "profile__account_type:custAcctType_" + ID
  }

  def getCustomerAccoutStatusSearchString(ID: Int) : String = {
    "profile__customer_account_status:custAcctStatus_" + ID
  }

}
