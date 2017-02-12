import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Crawler {

    /**
     * It creates a spider (which creates spider legs) and crawls the web.
     *
     * @param args - args[0] - the website to crawl
     */

    public static Map<String, List<String>> output = new LinkedHashMap<String, List<String>>();

    public static void printJSONUrls() throws FileNotFoundException {
        //useful for creating the json object.
        List<UrlsMap> urlsMapList = new ArrayList<UrlsMap>();
        for (Map.Entry<String, List<String>> entry : output.entrySet()) {
            UrlsMap urlsMap = new UrlsMap(entry.getKey(), entry.getValue());
            urlsMapList.add(urlsMap);
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(urlsMapList);

//        PrintWriter out = new PrintWriter("/tmp/output.txt");
        System.out.println(json);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Spider spider = new Spider();

        try {
            spider.search(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printJSONUrls();
        }

    }
}
