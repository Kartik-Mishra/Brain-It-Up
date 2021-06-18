package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IQ_FullForm extends AppCompatActivity {

    RadioGroup iq;
    RadioButton iq1,iq2,iq3,iq4;
    Button check;
    public String IQ_CHECK_KEY = "Bhai ka iq ka parinaam";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iq__full_form);

        iq = (RadioGroup ) findViewById(R.id.iq_rg);

      iq1 = (RadioButton ) findViewById(R.id.iq1);
        iq2 = (RadioButton ) findViewById(R.id.iq2);
        iq3 = (RadioButton ) findViewById(R.id.iq3);
        iq4 = (RadioButton ) findViewById(R.id.iq4);

        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(iq1.isChecked()||iq2.isChecked()||iq3.isChecked()||iq4.isChecked()) {
                SharedPreferences iqcheck = getSharedPreferences("Check", 0);
                SharedPreferences.Editor editor = iqcheck.edit();
                editor.putBoolean(IQ_CHECK_KEY, false);
                editor.commit();


                if (iq2.isChecked()) {
                    Dialog correct = new IQ_correct(IQ_FullForm.this);
                    correct.setCancelable(false);
                    correct.setCanceledOnTouchOutside(false);
                    correct.show();

                } else {
                    Dialog wrong = new IQ_wrong(IQ_FullForm.this);
                    wrong.setCancelable(false);
                    wrong.setCanceledOnTouchOutside(false);
                    wrong.show();

                }
            }else{
                Toast.makeText(IQ_FullForm.this,"Answer, then proceed",Toast.LENGTH_LONG).show();
            }
            }
        });




    }
}
