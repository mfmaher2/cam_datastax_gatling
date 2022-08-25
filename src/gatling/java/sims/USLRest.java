package sims;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.net.URLEncoder;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.concurrent.ThreadLocalRandom;

public class USLRest extends Simulation {
	
    public USLRest() throws Exception {
        //this.setUp(uslRestCall.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15))))
    	this.setUp(uslRestCall.injectOpen(rampUsers(users).during(rampIt)))
          .protocols(httpProtocol);
    }
    
    
    @Override
    public void before() {
      System.out.println("Simulation is about to start!");
    }

    @Override
    public void after() {
      System.out.println("Simulation is finished!");
    }
	
	String wsdlUrl = "http://vrh00741.ute.fedex.com:8080";
	FeederBuilder<String> feeder = separatedValues("input.txt",',').circular();
	
	int users = Integer.getInteger("users", 1);
	Long rampIt = java.lang.Long.getLong("ramp", 0L);
	Long testTime = java.lang.Long.getLong("testTime", 0L);
	
	Map<String,String> h1 = new HashMap<String,String>();
	
	//uslHeader.put("","");
	
	//sentHeaders.put("content-type", "applicationjavascript");
	//sentHeaders.put("accept", "texthtml");
	
    HttpProtocolBuilder httpProtocol =
            http.baseUrl(wsdlUrl)
                .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .acceptLanguageHeader("en-US,en;q=0.5")
                .acceptEncodingHeader("gzip, deflate")
                .userAgentHeader("gatling")
                .warmUp(wsdlUrl)
                ;
    /*
    Iterator<Map<String, Object>> feeder1 =
    	    Stream.generate((Supplier<Map<String, Object>>) ()
    	      -> Collections.singletonMap("username", UUID.randomUUID().toString())
    	    ).iterator();
    */
    
    	  // Scenario
    /*
    	  ScenarioBuilder scn = CoreDsl.scenario("Load Test Creating Customers")
    	    .feed(feeder)
    	    .exec(http("create-customer-request")
    	      .post("/api/customers")
    	      .header("Content-Type", "application/json")
    	      .body(StringBody("{ \"username\": \"${username}\" }"))
    	      .check(status().is(201))
    	      .check(header("Location").saveAs("location"))
    	    )
    	    .exec(http("get-customer-request")
    	      .get(session -> session.getString("location"))
    	      .check(status().is(200))
    	    );
    	    
    	    
                       // .fileBody("accountInquiryRest.txt")
                       //  .get("/computers?f=#{searchCriterion}") // 4
    */
    	  
    ChainBuilder inquire =
    		 exec(session -> {
    			  Session newSession = session.set("accountNumber", "${accountNumber}");
    			  System.out.println(newSession);
    			  return newSession;
    			})
    		 .exec(
                    http("accountInquiry")
                        .post("/service/rest/accountInquiry")
                        .header("X-CSR-SECURITY_TOKEN",URLEncoder.encode("MYTOKEN", "UTF-8"))
                        .header("keep-alive", "150")
                        .body(ElFileBody("requestBody/accountInquiryRest.txt"))
                        .check(status().is(200))
                );
    
    
    ScenarioBuilder uslRestCall = scenario("Users").feed(feeder).exec(inquire);
    

    
}
