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


public class Genius extends AppCompatActivity {
    private TextView question_view;
    private Button mNext;
    Music music = new Music();
    Chronometer timer;
    int currentQ = 0;
    RadioGroup genius_rg;
    RadioButton op1, op2, op3, op4,checked;
    private int score;
    int turns=0;
    boolean check= false, shuffle = true;

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<Integer> checkAns = new ArrayList<Integer>();


    //Keys
    private static final String Question_Index_key = "Index value of Questions";
    private static final String Checked_Radio = "Selected radio Button";
    private static final String Is_Radio_Checked = "ISC";
    private static final String Do_Not_Shuffle_Again = "Dont' shuffle";
    public static final String G_Result_Questions = "genius questions to display ";
    public static final String G_Result_Answers = "genius answers to display ";
    public static final String G_Check_Ans = " Genius : To show user - correct or incorrect";


    ArrayList<Genius_Questions> bank= new ArrayList<Genius_Questions>(){
        {
            add(new Genius_Questions("A man is very hungry. His stomach is absolutely empty. He have infinite chocolates with him.The man will be killed after 10 minutes.He started eating the chocolates one by one hurriedly. How many chocolates can that 80 kg weighed man eat in his absolutely empty stomach?(your answer should not be in decimal or fraction)","0","1","It may vary as per the wishes of that man.","80","1"));
            add(new Genius_Questions("Find the odd one out","0","1","2","3","1"));
            add( new Genius_Questions("There are seventy bottles on a table. If one falls down, then how many remain? (Voice)" , "7","6","0","68","6"));
            add(new Genius_Questions("If you choose an answer to this question at random, what is the chance you will be correct?", "25%", "0%", "50%", "25%", "0%"));
            add(new Genius_Questions("A pet shop has 8 hamsters, 18 rabbits and 7 guinea pigs. How many dogs does it have?","15","10","6","4","4"));
            add(new Genius_Questions("Complete the following sentence: \"Read every third spot young...\"", "Vagrant", "Psycho", "Doppelganger", "Warrior", "Psycho"));
            add(new Genius_Questions("?sdrawkcab nettirw iP ni srebmun ruof tsrif eht era tahW", "414.3", "141.3", "3.141", "411.3", "141.3"));
            add(new Genius_Questions("What are ID ten T mistakes?", "IDIOTS", "IDENTITY", "I didn't get it", "Useless question.", "IDIOTS"));
            add(new Genius_Questions("Clark does 5 sets of pushup, 6 sets of sit-up, 7 sets of squat, and 8 sets of bench dip in a day. So what do you think which organ in his body would be the largest?", "Muscles", "Skeleton", "Skin", "Nerves", "Skin"));
            add(new Genius_Questions("It took 20,000 workers to build the Taj Mahal in 20 years. How many workers would be required to build it in 10 years?", "10,000", "40,000", "30,000", "None of them", "None of them"));
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Question_Index_key,currentQ);
        outState.putInt(Checked_Radio,genius_rg.getCheckedRadioButtonId());
        outState.putBoolean(Is_Radio_Checked,check);
        outState.putBoolean(Do_Not_Shuffle_Again, shuffle);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Dialog alert_exit = new QuizExitAlert(Genius.this);
        alert_exit.show();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog alert_exit = new QuizExitAlert(Genius.this);
            alert_exit.show();

        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genius);

        //setting timer
        timer = (Chronometer) findViewById(R.id.timer_g);
        timer.setBase(SystemClock.elapsedRealtime() + (3 * 60000 + 0 * 1000));
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equalsIgnoreCase("00:05")){
                    timer.setTextColor(getResources().getColor(R.color.timer));
                }
                if (chronometer.getText().toString().equalsIgnoreCase("00:00")){
                    timer.stop();
                    new AlertDialog.Builder(Genius.this)
                            .setTitle("Hahaha!!!")
                            .setMessage("Your time is over")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Genius.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 3);
                                    i.putExtra(G_Result_Questions, questions);
                                    i.putExtra(G_Result_Answers, answers);
                                    i.putExtra(G_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Genius.this.finish();
                                }
                            }).setCancelable(false).show();
                }
            }
        });

        if(Settings.music(this)){
            music.play(this,R.raw.biu);
        }

        mNext = (Button) findViewById(R.id.next_genius);
        question_view = (TextView) findViewById(R.id.question_genius);
        genius_rg = (RadioGroup) findViewById(R.id.radio_genius);
        op1 = (RadioButton) findViewById(R.id.g_op1);
        op2 = (RadioButton) findViewById(R.id.g_op2);
        op3 = (RadioButton) findViewById(R.id.g_op3);
        op4 = (RadioButton) findViewById(R.id.g_op4);

        Collections.shuffle(bank);
        if(!Settings.darkTheme(this)){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.light_thme));
            question_view.setTextColor(Color.BLACK);
            timer.setTextColor(Color.BLACK);
            mNext.setBackgroundColor(getResources().getColor(R.color.light_button));
            op1.setTextColor(Color.BLACK);
            op2.setTextColor(Color.BLACK);
            op3.setTextColor(Color.BLACK);
            op4.setTextColor(Color.BLACK);
        }

        question_view.setText(bank.get(currentQ).getQ());
        op1.setText(bank.get(currentQ).getOp1());
        op2.setText(bank.get(currentQ).getOp2());
        op3.setText(bank.get(currentQ).getOp3());
        op4.setText(bank.get(currentQ).getOp4());



        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (turns >= 6) {
                    new AlertDialog.Builder(Genius.this)
                            .setTitle("Done!!!")
                            .setMessage("You have answered all the questions.")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Genius.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 3);
                                    i.putExtra(G_Result_Questions, questions);
                                    i.putExtra(G_Result_Answers, answers);
                                    i.putExtra(G_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Genius.this.finish();
                                }
                            }).show();

                }else {
                if (op1.isChecked() || op2.isChecked() || op3.isChecked() || op4.isChecked()) {
                    checked = (RadioButton) findViewById(genius_rg.getCheckedRadioButtonId());
                    turns++;
                    if (bank.get(currentQ).getAns() == checked.getText()) {
                        score += 10;
                        //Toast.makeText(Genius.this, "Correct", Toast.LENGTH_SHORT).show();
                        checkAns.add(1);
                    }else{
                        checkAns.add(0);
                    }
                    questions.add(bank.get(currentQ).getQ());
                    answers.add(bank.get(currentQ).getAns());

                    genius_rg.clearCheck();
                    currentQ++;
                    if (5 > turns) {
                        question_view.setText(bank.get(currentQ).getQ());
                        op1.setText(bank.get(currentQ).getOp1());
                        op2.setText(bank.get(currentQ).getOp2());
                        op3.setText(bank.get(currentQ).getOp3());
                        op4.setText(bank.get(currentQ).getOp4());

                    } else {
                        turns++;
                        new AlertDialog.Builder(Genius.this)
                                .setTitle("Done!!!")
                                .setMessage("You have answered all the questions.")
                                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(Genius.this,Score.class);
                                        i.putExtra(new Score().Score_key, score);
                                        i.putExtra(new Score().Level_key, 3);
                                        i.putExtra(G_Result_Questions, questions);
                                        i.putExtra(G_Result_Answers, answers);
                                        i.putExtra(G_Check_Ans, checkAns);
                                        i.putExtra("Total no. of questions attemped", turns);

                                        startActivity(i);
                                        Genius.this.finish();
                                    }
                                }).show();
                    }

                } else {
                    Toast.makeText(Genius.this, "Select an option to proceed", Toast.LENGTH_SHORT).show();
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
        if (Settings.music(this)) {
            music.resume();
        }
    }
}





