package com.example.b.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b.R;
import com.example.b.controller.LinkImageViewer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    private Bundle b;

    @Override
    protected void onStart() {
        super.onStart();
        if (b != null && b.getString("FROM") != null) {
            if (b.getString("FROM").equals("OK")) {
                String imageURL = b.getString("IMAGE_LINK");

                DateFormat df = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
                Date today = Calendar.getInstance().getTime();
                String imageDate = df.format(today);

                intent = new Intent();
                intent.setAction("sendToDatabase");
                intent.putExtra("IMAGE_URL", imageURL);
                intent.putExtra("IMAGE_DATE", imageDate);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                new LinkImageViewer((ImageView) findViewById(R.id.imageView)).execute(imageURL);
            } else {
                String imageURL = b.getString("IMAGE_LINK");
                int imageStatus = b.getInt("IMAGE_STATUS");

                ImageView imageView = findViewById(R.id.imageView);

                Picasso.get().load(imageURL).into(imageView);

                if (imageStatus == 1) {
                    new CountDownTimer(15000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            showToast("CLOSED");
                            finish();
                        }
                    }.start();
                }
            }
        } else {
            closeApp();
        }
    }

    class LinkImageViewer extends AsyncTask<String, Void, Bitmap> {
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
                intent.putExtra("IMAGE_STATUS", 1);
                sendBroadcast(intent);
            } catch (Exception e) {
                intent.putExtra("IMAGE_STATUS", 2);
                sendBroadcast(intent);
            }
            return Image;
        }

        protected void onPostExecute(Bitmap result) {
            bitmapImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = getIntent().getExtras();
    }

    private void showToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }

    private void closeApp() {
        showToast("Приложение В не является самостоятельным приложением и будет закрыто через 10 секунд");
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                finish();
            }
        }.start();
    }
}