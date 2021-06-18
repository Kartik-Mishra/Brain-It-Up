package com.itrio.iqtest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome_3 extends AppCompatActivity {
    Button mPrev, mGotit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_3);

        mGotit = (Button) findViewById(R.id.gotit);
        mPrev = (Button) findViewById(R.id.wel3prev);

        mGotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wel3 = new Intent(Welcome_3.this, IQ_FullForm.class);
                startActivity(wel3);
                Welcome_3.this.finish();

            }
        });
        mPrev.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent wel3 = new Intent(Welcome_3.this, Welcome_2.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.exit,R.anim.enter).toBundle();
                startActivity(wel3,bndlanimation);
                Welcome_3.this.finish();

            }
        });
    }
}
