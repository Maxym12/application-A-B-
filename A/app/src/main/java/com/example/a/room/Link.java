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

    @Ignore
    public Link(String imageLink) {
        this.imageLink = imageLink;
    }

    public Link(long id, String imageLink, int status) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
    }

    @Override
    public String toString() {
        return imageLink;
    }
}