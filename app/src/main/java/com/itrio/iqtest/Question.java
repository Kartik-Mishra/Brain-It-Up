package com.itrio.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Question extends Activity {
    TextView que;
    RadioButton r1,r2,r3,r4;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        que=(TextView)findViewById(R.id.que);
        r1=(RadioButton)findViewById(R.id.op1);
        r2=(RadioButton)findViewById(R.id.op2);
        r3=(RadioButton)findViewById(R.id.op3);
        r4=(RadioButton)findViewById(R.id.op4);
        b=(Button)findViewById(R.id.check);
        final     SharedPreferences sp= getSharedPreferences("information",0);
        String fullname=   sp.getString("Bhai ka naam" , null);
        if (sp.getBoolean("gender" , true))
        {

            que.setText("So "+fullname+", let's do a little warm up. Answer this easy question:\nIf 'A' is your father. But you are not his son! Then how are you related to him?");
            r2.setText("Obviously Daughter");
        }
        else
        {
            que.setText("So "+fullname+", let's do a little warm up. Answer this easy question:\nIf 'A' is your father. But you are not his daughter! Then how are you related to him?");
            r2.setText("Obviously Son");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!r1.isChecked() && !r2.isChecked() && !r3.isChecked() && !r4.isChecked())
                {
                    text();
                }
                else {
                    Context c = Question.this;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(false);
                    if (r4.isChecked()) {
                        builder.setTitle("Yes..");
                        builder.setMessage("You are ready for the Quiz BRAIN IT UP! I was kidding.");
                        builder.setView(R.layout.imogi);
                    }
                    if (r1.isChecked() || r3.isChecked()) {
                        builder.setTitle("No.");
                        builder.setMessage("The answer is 4th option. I was kidding.");
                        builder.setView(R.layout.imogi);
                    }
                    if (r2.isChecked())
                    {
                        if(sp.getBoolean("gender" , true)) {
                            builder.setTitle("But you told you are Male!");
                            builder.setMessage("Hence your answer(daughter) is wrong. This was an easy one and you will face more such questions. Just BRAIN IT UP! The answer is option 4.  I was kidding.");
                        }
                        else{
                            builder.setTitle("But you told you are Female!");
                            builder.setMessage("Hence your answer(son) is wrong. This was an easy one and you will face more such questions. Just BRAIN IT UP! The answer is option 4.  I was kidding.");
                        }
                        builder.setView(R.layout.imogi);
                    }
                    AlertDialog.Builder dialog=  builder.setPositiveButton("Let's Proceed!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Question.this, MainMenu.class));
                            Question.this.finish();

                        }
                    });
                    builder.create().show();

                }
            }
        });
    }
    final void text()
    {
        Toast.makeText(this,"Select any one",Toast.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    RelativeLayout r=(RelativeLayout)findViewById(R.id.hihi);
        if(Settings.darkTheme(this))
        {
            r.setBackgroundColor(Color.parseColor("#d7000000"));
        }
        else
            r.setBackgroundColor(Color.parseColor("#d7ffffff"));
            */
    }
}