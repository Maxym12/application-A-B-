package com.example.a.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Link {

    @PrimaryKey
    public long id;

    public String imageLink;

    public int status;

    public String date;

    @Ignore
    public Link(String imageLink) {
        this.imageLink = imageLink;
    }

    @Ignore
    public Link(long id, String imageLink, int status) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
    }

    public Link(long id, String imageLink, int status, String date) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString() {
        return imageLink;
    }
}