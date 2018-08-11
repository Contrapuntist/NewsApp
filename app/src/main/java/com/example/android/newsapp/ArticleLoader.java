package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private static String LOG_TAG = ArticleLoader.class.getName();
    private String queryString;

    public ArticleLoader(Context context, String queryURL) {
        super(context);
        queryString = queryURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        URL queryURL = QueryUtils.createURL(queryString);
        String APIResponse = QueryUtils.httpRequest(queryURL);
        List<Article> articlesList = null;
        try {
            articlesList = QueryUtils.extractArticleResultsFromJSON(APIResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return articlesList;
    }
}
