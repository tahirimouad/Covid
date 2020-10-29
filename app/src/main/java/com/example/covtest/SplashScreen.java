package com.example.covtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageViewLogo = findViewById(R.id.imv_logo);
        TextView slogon = (TextView) findViewById(R.id.slogon);
        Animation topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this,R.anim.buttom_animation);
        Animation rotateAnim = AnimationUtils.loadAnimation(this,R.anim.rotation_animation);


        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        //s.addAnimation(topAnim);
        s.addAnimation(topAnim);
        s.addAnimation(rotateAnim);
        imageViewLogo.startAnimation(s);

        slogon.setAnimation(bottomAnim);
        /*imageViewLogo.setAnimation(topAnim);
        imageViewLogo.setAnimation(bottomAnim);
        imageViewLogo.setAnimation(rotateAnim);*/

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}