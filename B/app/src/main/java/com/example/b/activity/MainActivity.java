package com.example.b.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.health.TimerStat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle b;
    private boolean flag;
    private boolean flag2;
    private Bitmap image;

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
                intent.putExtra("FOR", "INSERT");
                intent.putExtra("IMAGE_URL", imageURL);
                intent.putExtra("IMAGE_DATE", imageDate);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                flag2 = true;
                new LinkImageViewer((ImageView) findViewById(R.id.imageView)).execute(imageURL);
            } else if (b.getString("FROM").equals("HISTORY")) {
                final String imageURL = b.getString("IMAGE_LINK");
                int imageStatus = b.getInt("IMAGE_STATUS");
                int imageID = b.getInt("IMAGE_ID");

                if (imageStatus == 1) {
                    intent = new Intent();
                    intent.setAction("sendToDatabase");
                    intent.putExtra("FOR", "DELETE");
                    intent.putExtra("IMAGE_ID", imageID);
                    intent.putExtra("IMAGE_URL", imageURL);
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                    flag = false;
                    flag2 = false;
                    new LinkImageViewer((ImageView) findViewById(R.id.imageView)).execute(imageURL);

                    new CountDownTimer(15000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            saveOnSDCard(imageURL);
                            sendBroadcast(intent);
                            showToast("Ссылка : " + imageURL + " была удалена");
                        }
                    }.start();
                } else {
                    intent = new Intent();
                    intent.setAction("sendToDatabase");
                    intent.putExtra("FOR", "UPDATE");
                    intent.putExtra("IMAGE_ID", imageID);
                    intent.putExtra("IMAGE_DATE", b.getString("IMAGE_DATE"));
                    intent.putExtra("IMAGE_URL", b.getString("IMAGE_URL"));
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                    flag = true;
                    flag2 = false;
                    new LinkImageViewer((ImageView) findViewById(R.id.imageView)).execute(imageURL);
                }
            }
        } else {
            closeApp();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = getIntent().getExtras();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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

    public void saveOnSDCard(String url) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/BIGDIG/test/B");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] array = url.split("/");
        url = array[array.length - 1];
        url = url.substring(0, url.indexOf("."));

        File file = new File(dir, url + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (IOException ioe) {
            showToast("Ошибка " + ioe.toString());
        }
    }

    private class LinkImageViewer extends AsyncTask<String, Void, Bitmap> {
        ImageView bitmapImage;

        public LinkImageViewer(ImageView bitmapImage) {
            this.bitmapImage = bitmapImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap Image = null;
            try {
                URL imageUrl = new URL(url);
                InputStream inputStream = imageUrl.openStream();
                Image = BitmapFactory.decodeStream(inputStream);
                if (flag2) {
                    intent.putExtra("IMAGE_STATUS", 1);
                    sendBroadcast(intent);
                } else {
                    if (flag) {
                        intent.putExtra("IMAGE_STATUS", 1);
                        sendBroadcast(intent);
                    }
                }
            } catch (Exception e) {
                if (flag2) {
                    intent.putExtra("IMAGE_STATUS", 2);
                    sendBroadcast(intent);
                }
            }
            return Image;
        }

        protected void onPostExecute(Bitmap result) {
            image = result;
            bitmapImage.setImageBitmap(result);
        }
    }

}