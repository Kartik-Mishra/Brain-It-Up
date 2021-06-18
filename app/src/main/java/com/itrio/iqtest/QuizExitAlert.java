package com.itrio.iqtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by Admin on 14-May-17.
 */

public class QuizExitAlert extends Dialog {

    public Button mYes, mNo;
    Context forclass;

    QuizExitAlert(Context context){
        super(context);
        forclass = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_exit_alert);

        mYes = (Button) findViewById(R.id.alert_yes);
        mNo = (Button) findViewById(R.id.alert_no);

        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainmenu = new Intent(forclass, MainMenu.class);
                mainmenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                forclass.startActivity(mainmenu);
                ((Activity)forclass).finish();

            }
        });
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }
}
