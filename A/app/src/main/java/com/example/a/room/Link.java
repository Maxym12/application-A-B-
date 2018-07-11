package com.example.a.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Link {

    @PrimaryKey
    public int id;

    public String imageLink;

    public int status;

    public String date;

    @Ignore
    public static int lastID;

    @Ignore
    public Link(String imageLink) {
        this.imageLink = imageLink;
    }

    @Ignore
    public Link(int id, String imageLink, int status) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
    }

    public Link(int id, String imageLink, int status, String date) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
        this.date = date;
        lastID = id;
    }

    @Override
    public String toString() {
        return imageLink;
    }
}