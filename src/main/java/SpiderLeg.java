
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
    // Fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
            "AppleWebKit/535.1 (KHTML, like Gecko) " +
            "Chrome/13.0.782.112 " +
            "Safari/535.1";
    private List<String> links = new LinkedList<String>();


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page.
     *
     * @param url - The URL to visit
     * @param mainURL - The URL from we began
     * returns whether the crawling was successful or not
     */
    public boolean crawl(String url, String mainURL)
    {
        try
        {
            System.out.println("Trying to visit: " + url);
            //in case the webpage has SSL => the link will start with: https://, not http://
            String sslURL = "https" + mainURL.substring(4);
            //do not go on subdomains or other websites. stay on the main URL.
            if (!url.startsWith(mainURL) && !url.startsWith(sslURL)) {
                return false;
            }

            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();

            //only HTML pages. We don't care about something else.
            if(!connection.response().contentType().contains("text/html")) {
                System.out.println("No HTML page retrieved. Ignore.");
                return false;
            }
            //if the response status code is 200 then everything is alright.
            if(connection.response().statusCode() == 200) { // 200 is the HTTP OK status code
                System.out.println("Visiting " + url);
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
            Elements importsOnPage = htmlDocument.select("link[href]");
            Elements scriptElements = htmlDocument.getElementsByTag("script");
            Elements images = htmlDocument.select("img[src]");

            //retrieving all images from the url.
            for (Element image : images) {
                addToMap(url, mainURL, "src", image);
            }

            //retrieving script elements (e.g. js files)
            for (Element script : scriptElements ) {
                if (!script.attr("src").contains("www."))
                    addToMap(url, mainURL, "src", script);
            }

            //retrieving imports from url (e.g. css files)
            for (Element imports : importsOnPage) {
                addToMap(url, mainURL, "href", imports);
            }

            //retrieving links from url
            for(Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }

            System.out.println("Found " + linksOnPage.size() + " links." );
            return true;
        } catch (MalformedURLException e) {
             System.out.println("Not http or https protocol for url: " + url);
             e.printStackTrace();
             return false;
        } catch (HttpStatusException e) {
            System.out.println("Http error fetching URL  " + url);
            e.printStackTrace();
            return false;
        } catch (ConnectException e) {
            System.out.println("Could not connect to url: " + url);
            e.printStackTrace();
            return false;
        } catch (UnsupportedMimeTypeException e) {
            System.out.println("Content type not known for url: " + url);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong for : " + url);
            e.printStackTrace();
            return false;
        }
    }

    private void addToMap(String url, String mainURL, String attribute, Element link) {
        //the link.attribute may start with "/", e.g.: /images/example.jpg
        //in order to concatenate with our url we must delete this "/"
        // in the end we will have: www.example.com/images/example.jpg
        if (link.attr(attribute).startsWith("/")) {
            String asset;
            if (!url.endsWith("/"))
                asset = mainURL;
            else
                asset = mainURL.substring(0, mainURL.length() - 1);
            asset = asset + link.attr(attribute);

            //we add into the map the asset to the corresponding url.
            List<String> list = Crawler.output.get(url);
            if (list == null)
                list = new ArrayList<String>();
            list.add(asset);
            Crawler.output.put(url, list);
        }
    }

    public List<String> getLinks()
    {
        return this.links;
    }

}