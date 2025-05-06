package utils;

public class UrlBuilder {
    private static final String BASE_URL = ConfigReader.getProperty("home.page");

    public static String buildUrl(String path) {
        if (path == null || path.isEmpty()) {
            return BASE_URL;
        }
        if (path.startsWith("/")) {
            return BASE_URL + path;
        }
        return BASE_URL + "/" + path;
    }
}
