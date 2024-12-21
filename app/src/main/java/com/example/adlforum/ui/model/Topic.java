package com.example.adlforum.ui.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;
import java.time.format.DateTimeFormatter;

public class Topic {
    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("created_at")
    private DateTimeFormatter created_at;

    @SerializedName("updated_at")
    public DateTimeFormatter updated_at;

    @SerializedName("likes_count")
    private int likes_count;

    @SerializedName("status")
    private Boolean status;

    @SerializedName("author")
    public String author;

    @SerializedName("comments_count")
    public int comments_count;

    // Getters and Setters

    // Context-Based Constructor
    public Topic(Context context, String title, String content) {
        // Retrieve user data from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        this.user_id = Integer.parseInt(prefs.getString("id", "0")); // Default to 0 if ID is not found
        this.author = prefs.getString("username", "Unknown"); // Default to "Unknown" if username is not found
        this.title = title;
        this.content = content;
    }

    public String getIds() {
        return id;
    }

    public void setIds(String id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTimeFormatter getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(DateTimeFormatter created_at) {
        this.created_at = created_at;
    }

    public DateTimeFormatter getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(DateTimeFormatter updated_at) {
        this.updated_at = updated_at;
    }

    public int getLikesCount() {
        return likes_count;
    }

    public void setLikesCount(int likes_count) {
        this.likes_count = likes_count;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCommentsCount() {
        return comments_count;
    }

    public void setCommentsCount(int comments_count) {
        this.comments_count = comments_count;
    }
}
