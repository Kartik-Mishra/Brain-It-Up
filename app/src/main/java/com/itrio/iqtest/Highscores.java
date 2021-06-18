package com.itrio.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.itrio.iqtest.Score.getDefaults_easy;
import static com.itrio.iqtest.Score.getDefaults_gen;
import static com.itrio.iqtest.Score.getDefaults_hard;
import static com.itrio.iqtest.Score.getDefaults_nor;
import static com.itrio.iqtest.Score.getName_easy;
import static com.itrio.iqtest.Score.getName_gen;
import static com.itrio.iqtest.Score.getName_hard;
import static com.itrio.iqtest.Score.getName_nor;
import static com.itrio.iqtest.Score.nkey1;
import static com.itrio.iqtest.Score.nkey2;
import static com.itrio.iqtest.Score.nkey3;
import static com.itrio.iqtest.Score.nkey4;
import static com.itrio.iqtest.Score.setDefaults;


public class Highscores extends Activity {
    TextView t_easy , t_nor , t_hard , t_gen,main , name_e ,name_n , name_h , name_g;
    ImageView clear;
    String KEY_D = "Dailog";
    View checkBoxView;
    AlertDialog alert;
   public static String level_nor="level_nor";
    AlertDialog.Builder build;
    public static String level_hard="level_hard";
    public  static String level_gen="level_gen";
    static String KEY1 = "Score_e";
    static String KEY2 = "Score_n";
    static String KEY3 = "Score_h";
    static String KEY4 = "Score_g";
    static final int nor_score=50;
    static final int hard_score=50;
    static final int gen_score=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        t_easy = (TextView) findViewById(R.id.Text_e);
        t_nor = (TextView) findViewById(R.id.Text_n);
        t_hard = (TextView) findViewById(R.id.Text_h);
        t_gen = (TextView) findViewById(R.id.Text_g);
        main = (TextView) findViewById(R.id.textView);

        name_e = (TextView)findViewById(R.id.name_e);
        name_n = (TextView)findViewById(R.id.name_n);
        name_h = (TextView)findViewById(R.id.name_h);
        name_g = (TextView)findViewById(R.id.name_g);

        //Clear dialog box
        final Context c = this;
        build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle("Think Twice!");
        build.setView(checkBoxView);

        build.setMessage("Are you sure you want to clear all your scores and restart this page?");
        build.setPositiveButton("No Problem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                change();
                setDefaults(KEY1 , "0" ,nkey1,"", c);
                setDefaults(KEY2 , "0" ,nkey2,"", c);
                setDefaults(KEY3 , "0" ,nkey3,"", c);
                setDefaults(KEY4 , "0" ,nkey4,"", c);
                dialog.dismiss();
            }
        });
        build.setNegativeButton("Deny!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alert = build.create();



        main.setText("HIGHSCORES");
        checkBoxView = View.inflate(this, R.layout.checkbox, null);
        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);

        if (checkBox.isChecked()) {
            SharedPreferences sp = getSharedPreferences("dialog", 0);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(KEY_D, false);
            edit.apply();

        }


        t_easy.setText(getDefaults_easy(this));
        t_nor.setText(getDefaults_nor(this));
        t_hard.setText(getDefaults_hard(this));
        t_gen.setText(getDefaults_gen(this));

        name_e.setText(getName_easy(this));
        name_n.setText(getName_nor( this));
        name_h.setText(getName_hard(this));
        name_g.setText(getName_gen( this) );


        clear = (ImageView) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("dialog", 0);
                if (checkBox.isChecked()) {
                    change();
                } else
                    getPermissoin();

            }

        });
    }





    void text()
    {
        Toast.makeText(this , "Data Removed" , Toast.LENGTH_SHORT).show();
    }
    void getPermissoin()
    {
        alert.show();
    }
    void change()
    {
        text();

        final Context c = this;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY1, "0");
        editor.apply();

        editor.putString(KEY2, "0");
        editor.apply();
        editor.putString(KEY3, "0");
        editor.apply();

        editor.putString(KEY4, "0");
        editor.apply();

        editor.putString(nkey1, "");
        editor.apply();

        editor.putString(nkey2, "");
        editor.apply();
        editor.putString(nkey3, "");
        editor.apply();

        editor.putString(nkey4, "");
        editor.apply();
        recreate();



    }


}