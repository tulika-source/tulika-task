package com.demo.tulikaapplication.models;

public class ContentItems {
    public String  name;

    public String getName() {
        return name;
    }

    public ContentItems(String name, String poster_image) {
        this.name = name;
        this.poster_image = poster_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String  poster_image;
}
