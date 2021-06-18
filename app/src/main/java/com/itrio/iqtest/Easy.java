package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Easy extends AppCompatActivity {

    private TextView question_view;
    private Button mNext;
    Music music = new Music();
    int currentQ = 0;
    RadioGroup easy_rg;
    Chronometer timer;
    RadioButton op1, op2, op3, op4,checked;
    private int score=0;
    int turns=0 ;

    ArrayList<Easy_Questions> easy_bank = new ArrayList<Easy_Questions>(){
            {
                add(new Easy_Questions("What number should come next in the series:\n 2  1  1/2  1/4  __","1/5","1/6","1/8","1/16","1/8"));
                add(new Easy_Questions("Hot is to cold as dark is to:","Sun","Black","Light","Shade","Light"));
                add(new Easy_Questions("Which word does not belong:","Carrot","Letter","Apple","Lemon","Letter"));
                add(new Easy_Questions("Find the missing number:\n2  3  5  ?  11  13","6","7","8","9","7"));
                add(new Easy_Questions("What is 1/2 multiplied by 1/2?","1","0.5","0.25","0.75","0.25"));
                add(new Easy_Questions("Find the missing number:\n2  4  8  16  ?  64 ","20","32","44","56","32"));
                add(new Easy_Questions("Find the missing number:\n1  2  3  6  ?  24  48","10","12","16","20","12"));
                add(new Easy_Questions("Which number comes next in the following series?\n2  12  60  240  __","380","480","720","360","720"));
                add(new Easy_Questions("Which word doesn't fit?","Painting","Poem","Flower","Song","Flower"));
                add(new Easy_Questions("What day was yesterday, if Monday is in two days?","Sunday","Friday","Saturday","Wednesday","Friday"));
                add(new Easy_Questions("What is half of a quarter of 400?","25","50","100","200","50"));
                add(new Easy_Questions("Complete the series:\n" +
                        "3-6-10-20-24-?","28","32","40","48","48"));
                add(new Easy_Questions("Arrow is to bow as bullet is to:","Shoot","Gun","Bike","Lead","Gun"));
                add(new Easy_Questions("Gym is to Healthy as Book is to ?","Good","Intelligent","Smart","Knowledgeable","Knowledgeable"));
                add(new Easy_Questions("Which is the missing number: 3, 6, 18, 72, ?","144","100","360","90","360"));
                add(new Easy_Questions("Which one is least like others?","Dog","Lion","Snake","Pig","Snake"));
                add(new Easy_Questions("Find the one which does not belong to that group ?","7","3","4","5","4"));
                add(new Easy_Questions("How many degrees does the hour hand move in one hour?","360","120","30","60","30"));
            }
    };
    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<Integer> checkAns = new ArrayList<Integer>();

    //Keys
    public static final String E_Result_Questions = "easy genius questions to display ";
    public static final String E_Result_Answers = "easy genius answers to display ";
    public static final String E_Check_Ans = " Easy : To show user - correct or incorrect";
    private static final String Question_Index_key = "Index value of Questions";
    private static final String Checked_Radio = "Selected radio Button";



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Question_Index_key,currentQ);
        outState.putInt(Checked_Radio,easy_rg.getCheckedRadioButtonId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Dialog alert_exit = new QuizExitAlert(Easy.this);
        alert_exit.show();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Changes 'back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Dialog alert_exit = new QuizExitAlert(Easy.this);
            alert_exit.show();

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        //setting timer
        timer = (Chronometer) findViewById(R.id.timer_e);
        timer.setBase(SystemClock.elapsedRealtime() + (31000));
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equalsIgnoreCase("00:05")){
                    timer.setTextColor(getResources().getColor(R.color.timer));
                }

                if (chronometer.getText().toString().equalsIgnoreCase("00:00")){
                    timer.stop();
                    new AlertDialog.Builder(Easy.this)
                            .setTitle("Hahaha!!!")
                            .setMessage("Your time is over")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Easy.this, Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 0);
                                    i.putStringArrayListExtra(E_Result_Questions, questions);
                                    i.putIntegerArrayListExtra(E_Check_Ans, checkAns);
                                    i.putStringArrayListExtra(E_Result_Answers, answers);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Easy.this.finish();
                                }
                            }).setCancelable(false).show();
                }
            }
        });

        if (Settings.music(this)) {
            music.play(this, R.raw.biu);
        }


        mNext = (Button) findViewById(R.id.next_easy);
        question_view = (TextView) findViewById(R.id.question_easy);
        easy_rg = (RadioGroup) findViewById(R.id.radio_easy);
        op1 = (RadioButton) findViewById(R.id.e_op1);
        op2 = (RadioButton) findViewById(R.id.e_op2);
        op3 = (RadioButton) findViewById(R.id.e_op3);
        op4 = (RadioButton) findViewById(R.id.e_op4);

        Collections.shuffle(easy_bank);

        if (!Settings.darkTheme(this)) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.light_thme));
            question_view.setTextColor(Color.BLACK);
            timer.setTextColor(Color.BLACK);
            mNext.setBackgroundColor(getResources().getColor(R.color.light_button));
            op1.setTextColor(Color.BLACK);
            op2.setTextColor(Color.BLACK);
            op3.setTextColor(Color.BLACK);
            op4.setTextColor(Color.BLACK);
        }

        question_view.setText(easy_bank.get(currentQ).getQ());

        op1.setText(easy_bank.get(currentQ).getOp1());
        op2.setText(easy_bank.get(currentQ).getOp2());
        op3.setText(easy_bank.get(currentQ).getOp3());
        op4.setText(easy_bank.get(currentQ).getOp4());


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (turns >= 11) {
                    new AlertDialog.Builder(Easy.this)
                            .setTitle("Done!!!")
                            .setMessage("You have answered all the questions.")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Easy.this, Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 0);
                                    i.putStringArrayListExtra(E_Result_Questions, questions);
                                    i.putIntegerArrayListExtra(E_Check_Ans, checkAns);
                                    i.putStringArrayListExtra(E_Result_Answers, answers);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Easy.this.finish();
                                }
                            }).show();

                } else {

                    if (op1.isChecked() || op2.isChecked() || op3.isChecked() || op4.isChecked()) {
                        checked = (RadioButton) findViewById(easy_rg.getCheckedRadioButtonId());
                        turns++;

                        if (easy_bank.get(currentQ).getAns() == checked.getText()) {
                            score += 10;
                            checkAns.add(1);
                        } else {
                            checkAns.add(0);
                        }

                        questions.add(easy_bank.get(currentQ).getQ());
                        answers.add(easy_bank.get(currentQ).getAns());
                        easy_rg.clearCheck();

                        currentQ++;

                        if (10 > turns) {
                            question_view.setText(easy_bank.get(currentQ).getQ());

                            op1.setText(easy_bank.get(currentQ).getOp1());
                            op2.setText(easy_bank.get(currentQ).getOp2());
                            op3.setText(easy_bank.get(currentQ).getOp3());
                            op4.setText(easy_bank.get(currentQ).getOp4());

                        } else {
                            turns++;
                            new AlertDialog.Builder(Easy.this)
                                    .setTitle("Done!!!")
                                    .setMessage("You have answered all the questions.")
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(Easy.this, Score.class);
                                            i.putExtra(new Score().Score_key, score);
                                            i.putExtra(new Score().Level_key, 0);
                                            i.putStringArrayListExtra(E_Result_Questions, questions);
                                            i.putIntegerArrayListExtra(E_Check_Ans, checkAns);
                                            i.putStringArrayListExtra(E_Result_Answers, answers);
                                            i.putExtra("Total no. of questions attemped", turns);
                                            startActivity(i);
                                            Easy.this.finish();
                                        }
                                    }).show();

                        }
                    } else {
                        Toast.makeText(Easy.this, "Select an option to proceed", Toast.LENGTH_SHORT).show();

                    }
                }

            }

        });

    }



    @Override
    protected void onPause() {
        super.onPause();
        if(Settings.music(this)) {
            music.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.awake(this)) {
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "");
            mWakeLock.acquire();
        }
        if(Settings.music(this)) {
            music.resume();
        }
    }
}
