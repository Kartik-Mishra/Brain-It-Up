package com.itrio.iqtest;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {


    String name;
    TextView hi;
    String user_age;
    EditText username, age;
    Button mSubmit;
    RadioGroup gender;
    RadioButton male, female;

   public String KEY_USERNAME = "Bhai ka naam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        username = (EditText) findViewById(R.id.username);
        name = username.getText().toString();
        hi = (TextView) findViewById(R.id.hi);
        age = (EditText) findViewById(R.id.age_user);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male) ;
        female = (RadioButton) findViewById(R.id.female) ;


       username.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = username.getText().toString();
               hi.setText("Hi, " + name );

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        mSubmit= (Button) findViewById(R.id.user_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean sex;
                user_age = age.getText().toString();

                name = username.getText().toString();
                if ( name.isEmpty()|| name.matches("")|| name.matches(" ")|| name.length() == 0 || user_age == null || user_age.isEmpty() || user_age.matches("")|| user_age.length()==0 ) {
                    Toast.makeText(UserInfo.this, "Enter you name and age", Toast.LENGTH_LONG).show();
                } else {

                    SharedPreferences info = getSharedPreferences("information", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = info.edit();
                    edit.putString(KEY_USERNAME,name);
                    edit.putString("age",user_age);
                    edit.apply();

                    notifyinfo();

                    if(male.isChecked()){
                        sex = true;
                        edit.putBoolean("gender",sex);
                        edit.apply();
                        notifyinfo();
                        Intent mainmenu = new Intent(UserInfo.this, Question.class);
                        startActivity(mainmenu);
                        UserInfo.this.finish();
                    }
                    else if(female.isChecked()){
                        sex=false;
                        edit.putBoolean("gender",sex);
                        edit.apply();
                        notifyinfo();
                        Intent mainmenu = new Intent(UserInfo.this, Question.class);
                        startActivity(mainmenu);
                        UserInfo.this.finish();

                    }
                    else{
                        Toast.makeText(UserInfo.this,"Choose your gender",Toast.LENGTH_LONG).show();
                    }





                }
            }
              });

        }
    public void notifyinfo(){
        /*
        NotificationCompat.Builder note = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Information stored successfully").setContentText("  ");
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, note.build());
    */
    }
}
