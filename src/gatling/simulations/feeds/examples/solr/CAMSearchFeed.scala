package feeds.examples.solr

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

import java.util.Random


class CAMSearchFeed extends BaseFeed with LazyLogging {

  val random = new Random()

    def readSearch = Iterator.continually(getSearchData)

    private def getSearchData = {
      val searchString = "person__first_name:*" + (random.nextInt(100000) + 1)
      Map("solr_query" -> searchString)
    }
}
