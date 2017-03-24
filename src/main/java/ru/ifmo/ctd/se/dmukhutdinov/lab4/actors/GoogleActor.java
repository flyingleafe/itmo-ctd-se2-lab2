package ru.ifmo.ctd.se.dmukhutdinov.lab4.actors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.logic.Util;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.model.SearchResult;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flyingleafe
 */
public class GoogleActor extends AbstractChildActor {
    private static final String QUERY_URL = "https://www.googleapis.com/customsearch/v1?" +
            "cx=016587339558293533469:qsk-os2vnk4&" +
            "key=AIzaSyAFuQcL3HJgc0m6VrOsNqCfS5Io3AdS5TQ&q=";

    @Override
    public List<SearchResult> getSearchResults(String query) {
        try {
            HttpsURLConnection conn = Util.httpsGet(QUERY_URL + Util.urlEncode(query));
            JsonObject answer = Util.jsonFromStream(conn.getInputStream());

            List<SearchResult> res = new ArrayList<>();
            for (JsonElement el : answer.getAsJsonArray("items")) {
                JsonObject o = el.getAsJsonObject();
                res.add(new SearchResult(
                        "Google",
                        o.get("title").getAsString(),
                        o.get("link").getAsString(),
                        o.get("snippet").getAsString()));
            }
            return res;
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }
}
