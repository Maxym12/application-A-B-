package com.example.b.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.b.activity.MainActivity;

import java.net.URL;

import java.io.InputStream;

public class LinkImageViewer extends AsyncTask<String, Void, Bitmap> {
    ImageView bitmapImage;

    public LinkImageViewer(ImageView bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap Image = null;
        try {
            URL imageUrl = new URL(url);
            InputStream inputStream = imageUrl.openStream();
            Image = BitmapFactory.decodeStream(inputStream);
            MainActivity.status = 1;
        } catch (Exception e) {
            MainActivity.status = 2;
        }
        return Image;
    }

    protected void onPostExecute(Bitmap result) {
        bitmapImage.setImageBitmap(result);
    }
}