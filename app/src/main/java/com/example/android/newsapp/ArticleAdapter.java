package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private String LOG_TAG = ArticleAdapter.class.getName();
    private Context activityContext;
    private List<Article> newsArticles;
    private int numberOfItemsForView;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, section, date, url;

        public LinearLayout parentLayout;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            section = itemView.findViewById(R.id.section);
            date = itemView.findViewById(R.id.date);
            url = itemView.findViewById(R.id.url);
            thumbnail = itemView.findViewById(R.id.article_image);
            parentLayout = itemView.findViewById(R.id.article_item_parent);
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int viewType) {

        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.article_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(layoutForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Article article = newsArticles.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.date.setText(article.getPubDate());
        holder.section.setText(article.getSection());
        holder.url.setText(R.string.read_article);

        Uri uri = Uri.parse(article.getImage());
        Picasso.get().load(uri).into(holder.thumbnail);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri webpage = Uri.parse(article.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(activityContext.getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }
}
