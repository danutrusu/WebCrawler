import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpiderTest {


    @Test
    public void testSearchFunction() {
        Spider spider = new Spider();
        spider.search("http://www.gocardless.com/blog/page35");
        assertEquals(true, Crawler.output.size() > 0);
        assertEquals(true, spider.getPageVisitedSize() > 0);
    }
}
