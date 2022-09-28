package sims.examples.cql.okta

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.concurrent.duration._
import scala.util.Random

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.{HttpURLConnection, URL}
import java.nio.charset.StandardCharsets
import java.util.Base64
import scala.io.Source
import scala.util.Random

import com.datastax.gatling.stress.core.BaseSimulation

class USLSoapOKTASimulation extends BaseSimulation {

  //val oktaToken = "eyJraWQiOiJGaEJNV0hDQlFTT080OG5PS3R0RjFrd2czTGRpYmI4anIydVk4ZEpVelBzIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkg4cGR4b3E2S2Y2Yi1nNWtvRlUtbWZxV004VWRxalMydldCQUJkRUVSOW8iLCJpc3MiOiJodHRwczovL3B1cnBsZWlkLXRlc3Qub2t0YXByZXZpZXcuY29tL29hdXRoMi9hdXMxMnptbDNxZW1DV1Q1TjBoOCIsImF1ZCI6IkFQUDQwMDQiLCJpYXQiOjE2NDkyNzE0MTAsImV4cCI6MTY0OTI3NTAxMCwiY2lkIjoiMG9hMTJ6bWNibXYzeWVxTHcwaDgiLCJzY3AiOlsiQ3VzdG9tX1Njb3BlIl0sInN1YiI6IjBvYTEyem1jYm12M3llcUx3MGg4IiwiZmR4X2VhaSI6IkFQUDc0OTUifQ.A6FJMskCM7TQfNHPUHmV6D0yvAhTTBOkFlCJgkV7sRcM_E7_9rKe2b23XnQ8GPjPmPP-1GeKOnEDjvS_9F7Z6wHGG52umlgufBeNgMkRigtUmb5VrzibABGvUBAY4o6dOn0OIGxeeZ7Li5HVAHtQUNTcQWN-4WCCrriBBZEQ4BEVt2P-rAPNFBp75N_ym_OZlAPnIi9EHVHjaif2afV-K2HuCCS-FKQua0CvYEWz2l-E1Zk8XxOZWVyUce9lUMrLzsSBTffAoEWvvteMDmvK-9eETuW177KFjXokT31uIEOaDRAK6-vj0Wpm7u4hojINL90Ozw7nzlX_Yw_EN-epeQ"
  //gather values from simulation config file

  val wsdlUrl = System.getProperty("restServiceCallWsdlUrl")
  val clientId = "0oa133tb5crhM8KyF0h8"
  val clientSecret = "TUplC-O4Fa7rlxSsmpzvD1eeTOpkFew9VpCnd7jl"
  val issuerUri = "https://purpleid-test.oktapreview.com/oauth2/aus133tdhez65vFRe0h8/v1/token"
  val scope = "Custom_Scope"
  // val wsdlUrl = "http://vrh00741.ute.fedex.com:8080/service/customerAccountService.v1.wsdl"

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  def userCount: Int = getProperty("users", "5").toInt
  def testDuration: Int = getProperty("testDurationMinutes", "5").toInt
  def rampUpUsersOverSeconds: Int = getProperty("rampUpUsersOverSeconds", "60").toInt
  def level: String = getProperty("level", "L4-cam-ws-gss")
  def tokenType: String = getProperty("tokenType", "okta")

  before {
    println(s"Running test with total of ${userCount + 5} users")
    println(s"Running test for ${testDuration} minutes")
    println(s"Ramp up ${userCount} users over ${rampUpUsersOverSeconds} seconds")
    println(s"Executing tests in level ${level}")
    println(s"Endpoint ${wsdlUrl}")
  }

  val httpConf = http
    .baseURL(wsdlUrl)
    .acceptHeader("text/xml, *; q=.2, */*; q=.2")
    .acceptEncodingHeader("gzip")
    .userAgentHeader("gatling")
    .warmUp(wsdlUrl)

  val headers_1 = Map(
    "Cache-Control" -> """no-cache""",
    "Content-Type" -> """text/xml; charset=utf-8""",
    "Pragma" -> """no-cache""",
    "SOAPAction" -> """""""")

  val scn = scenario("Scenario Name: AccountInquiry ")
    .forever() {
      feed(tsv("feeddata/single-account-number-list.txt").circular)
        .exec(session => session.set("securityToken", getOktaToken()))
        .exec(callAcccountInquiry())
    }

  def callAcccountInquiry() = {
    exec ( session =>
      session.set("securityToken", getOktaToken())
    )
      .exec(http("Account Inquiry REST")
        .post("/")
        .headers(headers_1)
        .body(ElFileBody("feeddata/accountInquiry.txt"))
        .check(status.is(200)))
  }

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsersPerSec(1) to userCount during rampUpUsersOverSeconds,
    ).protocols(httpConf)
  ).maxDuration(testDuration minutes)


  def getOktaToken() = {
    try {
      val tokenUri = issuerUri + "?grant_type=client_credentials&response_type=token&scope=" + scope
      System.setProperty("http.proxyHost", "internet.proxy.fedex.com")
      System.setProperty("http.proxyPort", "3128")
      System.setProperty("https.proxyHost", "internet.proxy.fedex.com")
      System.setProperty("https.proxyPort", "3128")
      val myUrl = new URL(tokenUri)
      val con = myUrl.openConnection.asInstanceOf[HttpURLConnection]
      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
      con.setRequestProperty("Accept", "application/json")
      val auth = clientId + ":" + clientSecret
      val encodedAuth = Base64.getEncoder.encode(auth.getBytes(StandardCharsets.UTF_8))
      val authHeaderValue = "Basic " + new String(encodedAuth)
      con.setRequestProperty("Authorization", authHeaderValue)
      con.setRequestMethod("POST")
      var content = ""
      try {
        content = Source.fromInputStream(con.getInputStream).mkString
      } catch {
        case e: Exception => println("Exception generating OKTA token")
      }
      val contentString = content.toString
      var accessToken = ""
      if (content.contains("\"access_token\":\"")) {
        accessToken = contentString.substring(contentString.indexOf("\"access_token\":\"") + 16)
        accessToken = accessToken.substring(0, accessToken.indexOf("\""))
      }
      accessToken
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}