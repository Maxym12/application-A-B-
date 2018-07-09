package com.example.b;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("FROM").equals("OK")) {
                int imageID = b.getInt("IMAGE_ID");
                String imageURL = b.getString("IMAGE_LINK");

                intent = new Intent();
                intent.setAction("sendToDatabase");
                intent.putExtra("IMAGE_URL", imageURL);
                intent.putExtra("IMAGE_ID", imageID);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                ImageView imageView = findViewById(R.id.imageView);
                Picasso.get()
                        .load(imageURL)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                intent.putExtra("IMAGE_STATUS", 1);
                                sendBroadcast(intent);
                            }

                            @Override
                            public void onError(Exception e) {
                                intent.putExtra("IMAGE_STATUS", 2);
                                sendBroadcast(intent);
                            }
                        });
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
            showToast("Приложение В не является самостоятельным приложением и будет закрыто через 10 секунд");
            new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    showToast("CLOSED");
                    finish();
                }
            }.start();
        }

    }

    private void showToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
}