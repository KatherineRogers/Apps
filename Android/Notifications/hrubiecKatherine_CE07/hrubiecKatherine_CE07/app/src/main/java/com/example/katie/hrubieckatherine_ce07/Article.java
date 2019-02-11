package com.example.katie.hrubieckatherine_ce07;

import java.io.Serializable;

public class Article implements Serializable{

    final String title;
    final String urlToArticle;
    final String image;
    final String description;

    public Article(String title, String urlToArticle, String image, String description) {
        this.title = title;
        this.urlToArticle = urlToArticle;
        this.image = image;
        this.description = description;
    }

}
