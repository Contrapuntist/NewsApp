package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context activityContext;
    private static List<Article> newsArticles;

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, section, date, url;

        public ArticleViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            author = (TextView) v.findViewById(R.id.author);
            section = (TextView) v.findViewById(R.id.section);
            date = (TextView) v.findViewById(R.id.date);
            url = (TextView) v.findViewById(R.id.url);
        }

    }

    public ArticleAdapter(Context context,  List<Article> articles) {
        super();
        this.newsArticles = articles;
        this.activityContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = newsArticles.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.date.setText(article.getPubDate());
        holder.section.setText(article.getSection());
        holder.url.setText(article.getUrl());
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }


    public void setNewsArticles(List<Article> articles) {
        newsArticles = articles;
        this.notifyDataSetChanged();
    }
}
