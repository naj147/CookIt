package com.example.naj_t.cookitv1.AppLaunchActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.naj_t.cookitv1.R;

/**
 * Created by naj_t on 06/05/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ProductTourActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            }
        }, 500);
    }
}
