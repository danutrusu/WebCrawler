import java.util.List;

public class UrlsMap {
    private final String url;
    private final List<String> assets;

    public UrlsMap(String url, List<String> assets) {
        this.url = url;
        this.assets = assets;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getAssets() {
        return assets;
    }
}
