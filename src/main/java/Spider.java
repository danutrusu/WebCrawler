import java.util.*;

public class Spider {
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();


    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param url        - The starting point of the spider
     */
    public void search(String url) {
       try {
           String currentUrl;

           do {
               SpiderLeg leg = new SpiderLeg();
               if (this.pagesToVisit.isEmpty()) {
                   currentUrl = url;
                   this.pagesVisited.add(url);
               } else {
                   currentUrl = this.nextUrl();
               }
               leg.crawl(currentUrl, url); // Lots of stuff happening here. Look at the crawl method in
               this.pagesToVisit.addAll(leg.getLinks());

           } while (!this.pagesToVisit.isEmpty());

       } catch (IllegalArgumentException e) {
           e.printStackTrace();
       } finally {
           System.out.println("\nDone! Visited " + this.pagesVisited.size() + " web page(s)");
       }
    }

    /**
     * Returns the next URL to visit (in the order that they were found). We also do a check to make
     * sure this method doesn't return a URL that has already been visited.
     *
     * @return
     */
    private String nextUrl() {
        String nextUrl;
        do {
            if (!this.pagesToVisit.isEmpty()) {
                nextUrl = this.pagesToVisit.remove(0);
            } else {
                nextUrl = "";
            }
        } while (this.pagesVisited.contains(nextUrl));

        if (nextUrl.length() != 0)
            this.pagesVisited.add(nextUrl);

        return nextUrl;
    }

    public int getPageVisitedSize() {
        return pagesVisited.size();
    }

}
