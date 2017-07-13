package com.example.admin.posts;

/**
 * Created by admin on 7/8/2017.
 */

public class Posts {

    int id;
    int userId;
    String title;
    String body;

    public Posts(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
