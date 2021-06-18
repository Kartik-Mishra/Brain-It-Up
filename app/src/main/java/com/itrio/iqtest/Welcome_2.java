package com.itrio.iqtest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_2 extends AppCompatActivity {

    Button mNext,mPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_2);

        mNext = (Button) findViewById(R.id.wel2next);
        mPrev = (Button) findViewById(R.id.wel2prev);


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wel3 = new Intent(Welcome_2.this, Welcome_3.class);
                startActivity(wel3);
                Welcome_2.this.finish();


            }
        });
        mPrev.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent wel3 = new Intent(Welcome_2.this, Welcome.class);

                //Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.enter,R.anim.exit).toBundle();
                startActivity(wel3);
                Welcome_2.this.finish();
                overridePendingTransition(R.anim.exit,R.anim.enter);
            }
        });
    }
}
