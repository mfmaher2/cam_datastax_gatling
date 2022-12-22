package feeds.examples.solr

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

import java.util.Random


class CAMSearchFeed extends BaseFeed with LazyLogging {

  val random = new Random()

    def readSearch = Iterator.continually(getSearchData)

    private def getSearchData = {
      val queryType = random.nextInt(12)
//      val queryType = 6
      val id = (random.nextInt(100000) + 1)

      val searchString = queryType match {
        case 1 => generateSearchStringType1(id)
        case 2 => generateSearchStringType2(id)
        case 3 => generateSearchStringType3(id)
        case 4 => generateSearchStringType4(id)
        case 5 => generateSearchStringType5(id)
        case 6 => generateSearchStringType6(id)
        case 7 => generateSearchStringType7(id)
        case 8 => generateSearchStringType8(id)
        case 9 => generateSearchStringType9(id)
        case 10 => generateSearchStringType10(id)
        case 11 => generateSearchStringType11(id)
        case 12 => generateSearchStringType12(id)
        case _ => generateSearchStringType1(id)  //Simplest pattern of type 1 is default
      }

      Map("solr_query" -> searchString)
    }

  def generateSearchStringType1(ID:Int) : String = {
    //Single exact match parameter
    accountTypeCheck(ID)
  }

  def generateSearchStringType2(ID:Int) : String = {
    //Two exact match parameters
    accountTypeCheck(ID) + " && " +
      accountStatusCheck(ID)
  }

  def generateSearchStringType3(ID:Int) : String = {
    //Three exact match parameters
    accountTypeCheck(ID) + " && " +
      accountStatusCheck(ID) + " && " +
      countryCodeCheck(ID)
  }

  def generateSearchStringType4(ID:Int) : String = {
    //Four exact match parameters
    accountTypeCheck(ID) + " && " +
      accountStatusCheck(ID) + " && " +
      countryCodeCheck(ID)  + " && " +
      phoneNumberCheck(ID)
  }

  def generateSearchStringType5(ID:Int) : String = {
    //phone only exact match
    phoneNumberCheck(ID)
  }

  def generateSearchStringType6(ID:Int) : String = {
    //phone plus one other exact match
    accountTypeCheck(ID) + " && " +
      phoneNumberCheck(ID)
  }

  def generateSearchStringType7(ID:Int) : String = {
    //phone plus one other exact match and one wild card
    accountTypeCheck(ID) + " && " +
      personLastNameCheck(ID) + " && " +
      phoneNumberCheck(ID)
  }

  def generateSearchStringType8(ID:Int) : String = {
    //one wildcard match
    personFirstNameCheck(ID)
  }

  def generateSearchStringType9(ID:Int) : String = {
    //two wildcard match
    personFirstNameCheck(ID)  + " && " +
      companyNameCheck(ID)
  }

  def generateSearchStringType10(ID:Int) : String = {
    //three wildcard match
    personFirstNameCheck(ID)  + " && " +
      companyNameCheck(ID) + " && " +
      emailCheck(ID)
  }

  def generateSearchStringType11(ID:Int) : String = {
    //four  wildcard match
    personFirstNameCheck(ID)  + " && " +
      personLastNameCheck(ID) + " && " +
      companyNameCheck(ID) + " && " +
      emailCheck(ID)
  }

  def generateSearchStringType12(ID:Int) : String = {
    //four wildcard match and two each search match
    personFirstNameCheck(ID)  + " && " +
      personLastNameCheck(ID) + " && " +
      companyNameCheck(ID) + " && " +
      emailCheck(ID) + " && " +
      countryCodeCheck(ID) + " && " +
      accountStatusCheck(ID)
  }

  //** specific parameter creation helper functions ***
  //** exact match fields
  def accountTypeCheck(ID: Int): String = {
    "profile__account_type:custAcctType_" + ID
  }

  def accountStatusCheck(ID: Int): String = {
    "profile__customer_account_status:custAcctStatus_" + ID
  }

  def countryCodeCheck(ID: Int): String = {
    "address__country_code:addressCountryCode_" + ID
  }

  def phoneNumberCheck(ID:Int): String  = {
    "{!tuple}tele_com.phone_number:phoneNum_" + ID
  }

  //** wildcard match fields
  def personFirstNameCheck(ID: Int): String = {
    "person__first_name:*" + ID
  }

  def personLastNameCheck(ID: Int): String = {
    "person__last_name:*" + ID
  }

  def companyNameCheck(ID: Int): String = {
    "company_name:*" + ID
  }

  def emailCheck(ID: Int): String = {
    "email:*" + ID
  }
}
