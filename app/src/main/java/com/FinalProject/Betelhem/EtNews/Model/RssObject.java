package com.FinalProject.Betelhem.EtNews.Model;

import java.util.List;

public class RssObject
{
    public String version ;
    public String user_comment ;
    public String home_page_url ;
    public String feed_url ;
    public String title ;
    public String description ;
    public List<Item> items;

    public RssObject(String version, String user_comment, String home_page_url, String feed_url, String title, String description, List<Item> items) {
        this.version = version;
        this.user_comment = user_comment;
        this.home_page_url = home_page_url;
        this.feed_url = feed_url;
        this.title = title;
        this.description = description;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getHome_page_url() {
        return home_page_url;
    }

    public void setHome_page_url(String home_page_url) {
        this.home_page_url = home_page_url;
    }

    public String getFeed_url() {
        return feed_url;
    }

    public void setFeed_url(String feed_url) {
        this.feed_url = feed_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}