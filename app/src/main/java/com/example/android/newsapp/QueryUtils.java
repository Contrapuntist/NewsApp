package com.example.android.newsapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

public class QueryUtils {

    final static String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils() {
    }

    public static URL createURL(String stringURL) {
        URL queryURL = null;

        try {
            queryURL = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "error with creating URL", e);
        }

        return queryURL;
    }

    public static String httpRequest(URL requestURL) {
        String JSONresponse = "";
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        int resCode;

        if (requestURL == null) {
            return JSONresponse;
        }

        try {
            //http connection
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.connect();

            // get response code
            resCode = connection.getResponseCode();
            if (resCode == 200) {
                inputStream = connection.getInputStream();
                JSONresponse = readStream(inputStream);
            } else {
                Log.e(LOG_TAG, "RESPONSE CODE: " + resCode);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed HTTP connection", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return JSONresponse;
    }

    private static String readStream(InputStream inputStream) throws IOException {
        StringBuilder responseOutput = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                responseOutput.append(line);
                line = bufferedReader.readLine();
            }
        }

        return responseOutput.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Article> extractArticleResultsFromJSON(String newsJSON) {

        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            JSONObject articlesBaseJSON = new JSONObject(newsJSON);
            JSONObject response = articlesBaseJSON.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject articleObj = results.getJSONObject(i);

                String title = articleObj.getString("webTitle");
                String url = articleObj.getString("webUrl");
                String section = articleObj.getString("sectionId");

                String date = articleObj.getString("webPublicationDate");
                Instant instant = Instant.parse(date);
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
                String output = formatter.format(instant);

                JSONObject fields = articleObj.getJSONObject("fields");
                String author = fields.getString("byline");
                String image = fields.getString("thumbnail");

                articles.add(new Article(title, author, section, output, url, image));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error extracting results from JSON", e);
        }

        return articles;
    }
}
