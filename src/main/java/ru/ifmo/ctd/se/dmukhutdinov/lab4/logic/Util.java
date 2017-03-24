package ru.ifmo.ctd.se.dmukhutdinov.lab4.logic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.regexp.internal.RE;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.actors.QueryException;
import ru.ifmo.ctd.se.dmukhutdinov.lab4.actors.ResponseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

/**
 * @author flyingleafe
 */
public final class Util {
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
             throw new QueryException(e);
        }
    }

    public static HttpsURLConnection httpsGet(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (MalformedURLException | ProtocolException e) {
            throw new QueryException(e);
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }

    public static JsonObject jsonFromStream(InputStream inputStream) {
        Reader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        return new JsonParser().parse(reader).getAsJsonObject();
    }
}
