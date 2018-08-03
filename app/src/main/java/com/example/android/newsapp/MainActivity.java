package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>>{

    private TextView emptyView;
    private ProgressBar progressLoader;

    private NetworkInfo networkInfo;
    private List<Article> articles;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String queryString =
            "https://content.guardianapis.com/search?section=music&order-by=newest&show-fields=all&page-size=15&api-key=5e277810-8570-493e-9185-5d69d66ad663";

    private boolean isConnected;
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articles = new ArrayList<Article>();

        progressLoader = (ProgressBar) findViewById(R.id.progress_loader);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);


        ConnectivityManager connectionManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectionManager.getActiveNetworkInfo();
        isConnected = networkInfo != null &&
                              networkInfo.isConnected();
        if (isConnected) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            progressLoader.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Loader is getting created.");
        if (isConnected) {
            return new ArticleLoader(MainActivity.this, queryString);
        } else {
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        progressLoader.setVisibility(View.GONE);

        if (data != null && !data.isEmpty() ) {
            articles.addAll(data);
            mAdapter.notifyDataSetChanged();
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setText(R.string.no_results);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        articles.clear();
    }
}
