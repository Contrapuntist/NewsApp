package com.example.android.newsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private String LOG_TAG = ArticleAdapter.class.getName();
    private Context activityContext;
    private List<Article> newsArticles;
    private int numberOfItemsForView;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, section, date, url;
        public RelativeLayout cardParent;
        public CardView cardView;
        public LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            section = (TextView) itemView.findViewById(R.id.section);
            date = (TextView) itemView.findViewById(R.id.date);
            url = (TextView) itemView.findViewById(R.id.url);

            cardParent = (RelativeLayout)  itemView.findViewById(R.id.layout_in_card);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.article_item_parent);
        }
    }

    public ArticleAdapter(int numberOfItems) {
        numberOfItemsForView = numberOfItems;
    }

    public ArticleAdapter(Context context, List<Article> articles) {
        super();
        this.newsArticles = articles;
        this.activityContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType) {

        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.article_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(layoutForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.i(LOG_TAG, "onBindViewHolder: CALLED");

        final Article article = newsArticles.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.date.setText(article.getPubDate());
        holder.section.setText(article.getSection());
        holder.url.setText(article.getUrl());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "ITEM CLICKED at position: " + newsArticles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }
}
