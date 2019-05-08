package com.FinalProject.Betelhem.EtNews.Model;

import java.util.List;

/**
 * Created by voodoo on 11/10/2017.
 */

public class Item
{
    public String id ;
    public String url ;
    public String title ;
    public String content_html ;
    public String content_text ;
    public String date_published ;
    public String date_modified ;
    public Author author ;
    public List<String> tags ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_html() {
        return content_html;
    }

    public void setContent_html(String content_html) {
        this.content_html = content_html;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Item(String id, String url, String title, String content_html, String content_text, String date_published, String date_modified, Author author, List<String> tags) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.content_html = content_html;
        this.content_text = content_text;
        this.date_published = date_published;
        this.date_modified = date_modified;
        this.author = author;
        this.tags = tags;
    }
}