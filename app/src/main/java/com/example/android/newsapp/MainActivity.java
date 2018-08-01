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
    private ConnectivityManager connectionManager;
    private List<Article> articles;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String queryString =
            "https://content.guardianapis.com/search?section=music&production-office=US&order-by=newest&show-fields=all&page-size=15&api-key=5e277810-8570-493e-9185-5d69d66ad663";

    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articles = new ArrayList<Article>();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        connectionManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectionManager.getActiveNetworkInfo();

        boolean isConnected = networkInfo != null &&
                              networkInfo.isConnected();
        if (isConnected) {
            Log.i("NETWORK CHECK", "NETWORK IS CONNECTED IN ON CREATE");
            getLoaderManager().initLoader(0, null, this);
        } else {
            emptyView.setText("NO Internet Connection");
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Loader is getting created.");
        return new ArticleLoader(MainActivity.this, queryString);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
//        progressLoader.setVisibility(View.GONE);

        mAdapter = new ArticleAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }
}
