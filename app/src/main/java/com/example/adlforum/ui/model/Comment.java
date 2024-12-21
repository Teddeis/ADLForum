package com.example.adlforum.ui.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;
import java.time.format.DateTimeFormatter;

public class Comment {
    @SerializedName("id")
    private int id;
    @SerializedName("id_users")
    private int idUsers;
    @SerializedName("id_topics")
    private int idTopics;
    @SerializedName("author")
    private String author;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("comments")
    private String comments;

    // Конструктор
    public Comment(int id, int idUsers, int idTopics, String author, String avatar, String comments) {
        this.id = id;
        this.idUsers = idUsers;
        this.idTopics = idTopics;
        this.author = author;
        this.avatar = avatar;
        this.comments = comments;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdTopics() {
        return idTopics;
    }

    public void setIdTopics(int idTopics) {
        this.idTopics = idTopics;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
