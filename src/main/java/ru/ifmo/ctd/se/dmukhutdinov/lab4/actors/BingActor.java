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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flyingleafe
 */
public class BingActor extends AbstractChildActor {
    private static final String API_KEY = "278c6105c040438c854cd7b56e7d628c";
    private static final String QUERY_URL = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=";

    @Override
    public List<SearchResult> getSearchResults(String query) {
        try {
            HttpsURLConnection conn = Util.httpsGet(QUERY_URL + Util.urlEncode(query));
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);
            JsonObject answer = Util.jsonFromStream(conn.getInputStream());

            List<SearchResult> res = new ArrayList<>();
            for (JsonElement el : answer.get("webPages").getAsJsonObject().getAsJsonArray("value")) {
                JsonObject o = el.getAsJsonObject();
                res.add(new SearchResult(
                        "Bing",
                        o.get("name").getAsString(),
                        o.get("url").getAsString(),
                        o.get("snippet").getAsString()));
            }
            return res;
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }
}
