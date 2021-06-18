package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


public class Normal extends AppCompatActivity {

    Music music = new Music();
    private TextView question_view;
    private Button mNext;
    Switch theme;
    int currentQ = 0;
    RadioGroup normal_rg;
    RadioButton op1, op2, op3, op4,checked;
    private int score=0;
    Chronometer timer;
    int turns = 0;

    ArrayList<Normal_Questions> normal_bank = new ArrayList<Normal_Questions>(){
        {
            add(new Normal_Questions("Which word is the odd one out: First- Second- Third- Forth- Fifth- Sixth- Seventh- Eighth","Second","Third","Forth","Seventh","Forth"));
            add(new Normal_Questions("If a peacock can lay one egg on day 1, and lay two eggs on day 2; Logically how many eggs do you think the peacock can lay on day 3?","2","0","3","4","0"));
            add(new Normal_Questions("Which number comes next in the series:\n 3  4  5  8  11  12  ?","15","7","10","14","15"));
            add(new Normal_Questions("Fill in the missing number:\n0 1 1 2 3 5 8 13 __ 34 55","17","21","23","25","21"));
            add(new Normal_Questions("If you count from 1 to 80, how many 7's will you paas on the way?","8","10","17","18","18"));
            add(new Normal_Questions("Continue the series:\n A  C  G  M  __","T","R","S","U","U"));
            add(new Normal_Questions("Rearrange the word 'BELOW' to form a new word that is a : ","name of a city","name of a ocean","part of a body","part of a vehicle","part of a body"));
            add(new Normal_Questions("A clock shows 2pm. Through how many degrees has the hour hand rotated when the clock is showing 7:30pm?","120","150","165","180","165"));
            add(new Normal_Questions("If you rearrange the letters ETKARPEA you would have the name of a -","Animal","City","Metal","Bird","Bird"));
            add(new Normal_Questions("If circle is one, how many is an pentagon?","1","0","5","7","5"));
            add(new Normal_Questions("Find the next number in the series:\n 5  7  15  21  45  __","54","60","75","63","63"));
            add(new Normal_Questions("Which of the following does not belong to the series:\n 2  3  6  7  8  14  15  30","7","15","8","6","8"));
            add(new Normal_Questions("Which letter comes next in the series:\n B  A  C  B  D  C  E  D  F","E","F","G","H","E"));
            add(new Normal_Questions("Jacob and John, starting at the same point, walk in opposite directions for 12 meters. What is the distance between them?","24","13","26","10","26"));
            add(new Normal_Questions("If it were two hours later, it would be half as long until midnight as it would be if it were an hour later. What time is it now?","22:00","21:00","21:30","22:30","21:00"));
            add(new Normal_Questions("Continue the following number series:\n 1  10  3  9  5  8  7  7 __  __","6,5","9,6","8,6","10,5","9,6"));
            add(new Normal_Questions("At a birthday party, 10 people give gifts to each other. How many gifts were exchanged?","45","100","90","10","45"));
            add(new Normal_Questions("A farmer has 17 sheep and all but 9 die. How many are left?","8","9","0","17","9"));
            add(new Normal_Questions("What number should come next in the series:\n 100  98  95  90  83  72  __","47","53","59","64","59"));
            add(new Normal_Questions("What is the unknown number in the sequence:\n 16  24  __  54  81","30","36","45","50","45"));
            add(new Normal_Questions("What is the next word in the sequence:\n FAG, GAF, HAI, IAH, ___","JAK","HAL","HAK","JAI","JAI"));
            add(new Normal_Questions("You are in a race and you overtake the person who is in second place. What is your position now?","3rd","1st","2nd","Null","2nd"));

            add(new Normal_Questions("Max is two years older than Alice who is twice as old as Ben. If he sum of the ages of Max, Alice and Ben is 27, then how old is Max?","5","10","12","20","12"));


        }
    };
    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<Integer> checkAns = new ArrayList<Integer>();
    //Keys
    private static final String Question_Index_key = "Index value of Questions";
    private static final String Checked_Radio = "Selected radio Button";
    public static final String N_Result_Questions = "normal genius questions to display ";
    public static final String N_Result_Answers = "normal genius answers to display ";
    public static final String N_Check_Ans = " Normal : To show user - correct or incorrect";



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Question_Index_key,currentQ);
        outState.putInt(Checked_Radio,normal_rg.getCheckedRadioButtonId());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog alert_exit = new QuizExitAlert(Normal.this);
            alert_exit.show();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Dialog alert_exit = new QuizExitAlert(Normal.this);
        alert_exit.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        //setting timer
        timer = (Chronometer) findViewById(R.id.timer_n);
        timer.setBase(SystemClock.elapsedRealtime() + (1*60000));
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equalsIgnoreCase("00:05")){
                    timer.setTextColor(getResources().getColor(R.color.timer));
                }
                if (chronometer.getText().toString().equalsIgnoreCase("00:00")){
                    timer.stop();
                    new AlertDialog.Builder(Normal.this)
                            .setTitle("Hahaha!!!")
                            .setMessage("Your time is over")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Normal.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 1);
                                    i.putExtra(N_Result_Questions, questions);
                                    i.putExtra(N_Result_Answers, answers);
                                    i.putExtra(N_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Normal.this.finish();
                                }
                            }).setCancelable(false).show();
                }
            }
        });

        if(Settings.music(this)){
            music.play(this,R.raw.biu);
        }

        mNext = (Button) findViewById(R.id.next_normal);
        question_view = (TextView) findViewById(R.id.question_normal);
        normal_rg = (RadioGroup) findViewById(R.id.radio_normal) ;
        op1 = (RadioButton) findViewById(R.id.n_op1);
        op2 = (RadioButton) findViewById(R.id.n_op2);
        op3 = (RadioButton) findViewById(R.id.n_op3);
        op4 = (RadioButton) findViewById(R.id.n_op4);

        Collections.shuffle(normal_bank);

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

        question_view.setText(normal_bank.get(currentQ).getQ());

        op1.setText(normal_bank.get(currentQ).getOp1() );
        op2.setText(normal_bank.get(currentQ).getOp2() );
        op3.setText(normal_bank.get(currentQ).getOp3() );
        op4.setText(normal_bank.get(currentQ).getOp4() );


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (turns >= 11) {
                    new AlertDialog.Builder(Normal.this)
                            .setTitle("Done!!!")
                            .setMessage("You have answered all the questions.")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Normal.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 1);
                                    i.putExtra(N_Result_Questions, questions);
                                    i.putExtra(N_Result_Answers, answers);
                                    i.putExtra(N_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Normal.this.finish();
                                }
                            }).show();

                }
                else {

                    if (op1.isChecked() || op2.isChecked() || op3.isChecked() || op4.isChecked()) {
                        checked = (RadioButton) findViewById(normal_rg.getCheckedRadioButtonId());
                        turns++;
                        if (normal_bank.get(currentQ).getAns() == checked.getText()) {
                            score += 10;
                            checkAns.add(1);
                        }else{
                            checkAns.add(0);
                        }
                        questions.add(normal_bank.get(currentQ).getQ());
                        answers.add(normal_bank.get(currentQ).getAns());
                        normal_rg.clearCheck();

                        currentQ++;
                        if (10 > turns) {
                            question_view.setText(normal_bank.get(currentQ).getQ());

                            op1.setText(normal_bank.get(currentQ).getOp1());
                            op2.setText(normal_bank.get(currentQ).getOp2());
                            op3.setText(normal_bank.get(currentQ).getOp3());
                            op4.setText(normal_bank.get(currentQ).getOp4());
                        }
                        else{
                            turns++;
                            new AlertDialog.Builder(Normal.this)
                                    .setTitle("Done!!!")
                                    .setMessage("You have answered all the questions.")
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(Normal.this,Score.class);
                                            i.putExtra(new Score().Score_key, score);
                                            i.putExtra(new Score().Level_key, 1);
                                            i.putExtra(N_Result_Questions, questions);
                                            i.putExtra(N_Result_Answers, answers);
                                            i.putExtra(N_Check_Ans, checkAns);
                                            i.putExtra("Total no. of questions attemped", turns);
                                            startActivity(i);
                                            Normal.this.finish();
                                        }
                                    }).show();
                    }
                    }
                    else {
                        Toast.makeText(Normal.this, "Select an option to proceed", Toast.LENGTH_SHORT).show();
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
