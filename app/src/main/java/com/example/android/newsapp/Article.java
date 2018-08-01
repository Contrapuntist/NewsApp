package com.example.android.newsapp;

public class Article {

    private String title;
    private String author;
    private String section;
    private String pubDate;
    private String url;

    public Article(String articleTitle, String articleAuthor, String articleSection, String articlePublishDate, String articleUrl) {
        super();
        title = articleTitle;
        author = articleAuthor;
        section = articleSection;
        pubDate = articlePublishDate;
        url = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getUrl() {
        return url;
    }
}
