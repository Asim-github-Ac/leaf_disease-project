package com.example.saadi1.leafdetection;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Wait extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);


        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.gifss);


        Intent intent = getIntent();
        final Uri uri = intent.getParcelableExtra("uri");

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getBaseContext(), Result.class);
                    intent.putExtra("uri", uri);
                    startActivity(intent);

                    finish();

                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
