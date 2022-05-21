package com.example.saadi1.leafdetection;

import static com.example.saadi1.leafdetection.R.color.*;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));

        Intent intent = getIntent();
        final Uri uri = intent.getParcelableExtra("uri");
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getBaseContext(), MainPageActivity.class);
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
