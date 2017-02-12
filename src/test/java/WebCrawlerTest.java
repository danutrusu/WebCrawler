import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    @Test
    public void testObject() {
        List<String> assets = new ArrayList<String>();
        assets.add("www.gocardless.com/example.jpg");
        UrlsMap urlsMap = new UrlsMap("www.gocardless.com", assets);

        assertEquals("www.gocardless.com", urlsMap.getUrl());
        assertEquals(1, urlsMap.getAssets().size());
        assertEquals("www.gocardless.com/example.jpg", urlsMap.getAssets().get(0));
    }

    @Test
    public void testCrawlFunction() {
        SpiderLeg spiderLeg = new SpiderLeg();
        assertEquals(false, spiderLeg.crawl("", "www.google.com"));
        assertEquals(false, spiderLeg.crawl("www.example.com", "www.google.com"));
        assertEquals(true, spiderLeg.getLinks().size() == 0);
        assertEquals(true, Crawler.output.size() == 0);
        assertEquals(true, spiderLeg.crawl("http://www.google.com", "http://www.google.com"));
        assertEquals(true, spiderLeg.getLinks().size() > 0);
        assertEquals(true, Crawler.output.size() > 0);
    }


}
