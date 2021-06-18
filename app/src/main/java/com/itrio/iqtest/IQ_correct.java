package com.itrio.iqtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin on 15-May-17.
 */

public class IQ_correct extends Dialog {

    Button thanks;
    Context con;

    IQ_correct(Context context){
        super(context);
        con = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iq_dialog);

        thanks = (Button) findViewById(R.id.thank);

        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.startActivity(new Intent(con, UserInfo.class));
                ((Activity) con).finish();

            }
        });




    }
}
