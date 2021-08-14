package com.itrio.iqtest;
//jdskjl
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class About_us extends Activity {

    FloatingActionButton con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Contact Developers dialog
        final Dialog d = new ContactDeveloper(this);
        d.setCancelable(true);

        con = (FloatingActionButton) findViewById(R.id.con);
        con.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                        d.show();
                                   }
                               }
        );

    }

}
