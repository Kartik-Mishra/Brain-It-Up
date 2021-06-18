package com.itrio.iqtest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    protected final static int splash_time = 1500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefre=this.getSharedPreferences("firstt", Context.MODE_PRIVATE);
        boolean firsttimes=prefre.getBoolean("firstt", true);
        SharedPreferences info = getSharedPreferences("information",0);

        String name = info.getString(new UserInfo().KEY_USERNAME,null);

        SharedPreferences iqcheck = getSharedPreferences("Check",0);
        Boolean isTrue = iqcheck.getBoolean(new IQ_FullForm().IQ_CHECK_KEY,true);
        if(firsttimes) {
            SharedPreferences.Editor editere = prefre.edit();
            editere.putBoolean("firstt", false);
            editere.commit();
            startActivity(new Intent(Splash.this, Welcome.class));
            Splash.this.finish();


            }

        else if(name==null) {
            startActivity(new Intent(Splash.this, UserInfo.class));
            Splash.this.finish();


        }
        else if(isTrue){
            startActivity(new Intent(Splash.this,IQ_FullForm.class));
            Splash.this.finish();
        }
        else{
            Handler splash = new Handler();
            splash.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent go = new Intent(Splash.this,MainMenu.class);
                    startActivity(go);
                    Splash.this.finish();
                }

            },splash_time);
        }

    }
}
