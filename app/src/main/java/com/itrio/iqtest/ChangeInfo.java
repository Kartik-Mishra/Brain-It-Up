package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.support.v4.content.ContextCompat.startActivity;

public class ChangeInfo extends Dialog {

    Button mCancel, mApply;
    RadioGroup genChange;
    RadioButton male, female;
    EditText name, age;
    Context con;
    MainMenu m=new MainMenu();
    public  int check = 0;
    ChangeInfo(Context context){
        super(context);
        con = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_info);

        mApply = (Button) findViewById(R.id.change_apply);
        mCancel = (Button) findViewById(R.id.change_cancel);
        genChange = (RadioGroup) findViewById(R.id.gender_change);
        male = (RadioButton) findViewById(R.id.male_change);
        female = (RadioButton) findViewById(R.id.female_change);
        name = (EditText) findViewById(R.id.username_change);
        age = (EditText) findViewById(R.id.age_user_change);

        SharedPreferences info = con.getSharedPreferences("information", Context.MODE_PRIVATE);

        name.setText(info.getString(new UserInfo().KEY_USERNAME,""));
        age.setText(info.getString("age",""));



        mApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String new_name, ag;
                boolean gen;
                new_name = name.getText().toString();
                ag = age.getText().toString();
                if (male.isChecked()){
                    gen = true;
                }else {
                    gen= false;
                }
                SharedPreferences info = con.getSharedPreferences("information", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = info.edit();
                edit.putString(new UserInfo().KEY_USERNAME,new_name);
                edit.putString("age",ag);
                edit.putBoolean("gender",gen);
                edit.apply();
                check = 1;
                dismiss();
               // startActivity(new Intent(ChangeInfo.this, com.itrio.iqtest.MainMenu.class));
               // MainMenu.recreate();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
