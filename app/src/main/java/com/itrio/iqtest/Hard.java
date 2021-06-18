package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Hard extends AppCompatActivity {

    private TextView question_view;
    private Button mNext;
    static  Music music = new Music();
    int currentQ = 0;
    Switch theme;
    RadioGroup hard_rg;
    RadioButton op1, op2, op3, op4,checked;
    private int score= 0;
    int turns = 0;
    Chronometer timer;
    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<Integer> checkAns = new ArrayList<Integer>();

    //Keys

    static String KEY3 = "Score_h";

    public String Score_key = "score";
    public String Level_key = "level";
    private static final String Question_Index_key = "Index value of Questions";
    private static final String Checked_Radio = "Selected radio Button";

    public static final String H_Result_Questions = "hard questions to display at result activity";
    public static final String H_Check_Ans = " Hard : To show user - correct or incorrect";
    public static final String H_Result_Answers = "hard answers to display at result activity";

    ArrayList<Hard_Questions> hard_bank = new ArrayList<Hard_Questions>(){

        {
            add(new Hard_Questions("Rearrange the letters of the name SOCRATES to give two other eight letter words. What will be the first letter of both words?","T","R","C","S","C"));
            add(new Hard_Questions("A grandfather, a father and two sons went hunting, everyone shot a duck, how many ducks did they bring home?","3","2","0","4","3"));
            add(new Hard_Questions("If the second day of the month is a Monday, then the eighteenth day of the month is?(Don't see the calendar)","Tuesday","Wednesday","Monday","Thursday","Wednesday"));
            add(new Hard_Questions("What number best completes the following series?   1, 9, 25, __, 81, 100","50","36","46","60","36"));
            add(new Hard_Questions("Which one of the five choices makes the best comparison?\n" +
                    "PEACH is to HCAEP as 46251 is to:","25641","54126","26451","15264","15264"));
            add(new Hard_Questions("How many numbers from 1 - 100, which have the letter 'A' in their spellings?", "13", "54", "2", "0", "0"));
            add(new Hard_Questions("If you rearrange the letters \"CIFAIPC\" you would have the name of a(n):","City","Animal","Ocean","Country","Ocean"));
            add(new Hard_Questions("John needs 13 bottles of water from the store. John can only carry 3 at a time. What's the minimum number of trips John needs to make to the store?","3","4","4 1/2","5","5"));
            add(new Hard_Questions("If you rearrange the letters \"LNGEDNA\" you have the name of a(n):","City","Animal","Ocean","Country","Country"));
            add(new Hard_Questions("\tWhich one of the numbers does not belong in the following series?\n" +
                    "1 - 2 - 5 - 10 - 13 - 26 - 29 - 48","48","29","10","5","48"));
            add(new Hard_Questions("What is the missing number in the sequence shown below?\n" +
                    "1 - 8 - 27 - ? - 125 - 216","36","45","64","99","64"));
            add(new Hard_Questions("What letter is next in this sequence?\n" +
                    "O, T, T, F, F, S, S, E, __ ", "O", "E", "N", "G", "N"));
            add(new Hard_Questions("What letter is next in this sequence?\n" +
                    "M, A, M, J, J, A, S, O,__", "P", "N", "D", "F", "N"));
            add(new Hard_Questions("At a bakery it takes 3 people 18 minutes to decorate 15 cupcakes. How many cupcakes can 6 people complete within 1 hour? ","30","75","100","90","100"));
            add(new Hard_Questions("When the following letters are unscrambled, what you get can be use in a:\n H C P R A A T E U","Car","Swimming pool","Aeroplane","Stadium","Aeroplane"));
            add(new Hard_Questions("If MAJOR corresponds to 153121720, MOURN corresponds to -","2016112315","1517232016","1517202316","1712320122","1517232016"));
            add(new Hard_Questions("3 birds sitting on a tree branch, a hunter shoots and miss, how many birds left on the tree branch?","0","1","2","3","0"));
            add(new Hard_Questions("Samantha likes 324 but not 325; she likes 900 but not 800; she likes 169 but not 170. Which does she likes?","3500","3600","1000","2600","3600"));
            add(new Hard_Questions("Ralph likes 25 but not 24; he likes 400 but not 300; he likes 144 but not 145. Which does he like:","200","1600","100","50","1600"));
            add(new Hard_Questions("If each person shakes hands with six other persons, how many unique handshakes were performed in total?","21","42","12","6","21"));
            add(new Hard_Questions("What is the 4-digit number in which the first digit is one-fifth the last, and the second and third digits are the last digit multiplied by three?","1205","2010","1155","2310","1155"));
            add(new Hard_Questions("You are in a race and you overtake the person who is in second place. What is your position now?","3rd","1st","2nd","Null","2nd"));
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Question_Index_key,currentQ);
        outState.putInt(Checked_Radio,hard_rg.getCheckedRadioButtonId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Dialog alert_exit = new QuizExitAlert(Hard.this);
        alert_exit.show();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog alert_exit = new QuizExitAlert(Hard.this);
            alert_exit.show();

        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);

        //setting timer
        timer = (Chronometer) findViewById(R.id.timer_h);
        timer.setBase(SystemClock.elapsedRealtime() + (1*60000 + 40 * 1000));
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equalsIgnoreCase("00:05")){
                    timer.setTextColor(getResources().getColor(R.color.timer));
                }
                if (chronometer.getText().toString().equalsIgnoreCase("00:00")){
                    timer.stop();
                    new AlertDialog.Builder(Hard.this)
                            .setTitle("Hahaha!!!")
                            .setMessage("Your time is over")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Hard.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 2);
                                    i.putExtra(H_Result_Questions, questions);
                                    i.putExtra(H_Result_Answers, answers);
                                    i.putExtra(H_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Hard.this.finish();
                                }
                            }).setCancelable(false).show();
                }
            }
        });


        if(Settings.music(this)){
            music.play(this,R.raw.biu);
        }

        question_view = (TextView) findViewById(R.id.question_hard);
        mNext = (Button) findViewById(R.id.next_hard);
        hard_rg = (RadioGroup) findViewById(R.id.radio_hard) ;
        op1 = (RadioButton) findViewById(R.id.h_op1);
        op2 = (RadioButton) findViewById(R.id.h_op2);
        op3 = (RadioButton) findViewById(R.id.h_op3);
        op4 = (RadioButton) findViewById(R.id.h_op4);

        Collections.shuffle(hard_bank);

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

        question_view.setText(hard_bank.get(currentQ).getQ());

        op1.setText(hard_bank.get(currentQ).getOp1() );
        op2.setText(hard_bank.get(currentQ).getOp2() );
        op3.setText(hard_bank.get(currentQ).getOp3() );
        op4.setText(hard_bank.get(currentQ).getOp4() );





        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (turns >= 11) {
                    new AlertDialog.Builder(Hard.this)

                            .setTitle("Done!!!")
                            .setMessage("You have answered all the questions.")
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Hard.this,Score.class);
                                    i.putExtra(new Score().Score_key, score);
                                    i.putExtra(new Score().Level_key, 2);
                                    i.putExtra(H_Result_Questions, questions);
                                    i.putExtra(H_Result_Answers, answers);
                                    i.putExtra(H_Check_Ans, checkAns);
                                    i.putExtra("Total no. of questions attemped", turns);
                                    startActivity(i);
                                    Hard.this.finish();
                                }
                            }).show();

                }
                else{
                     if(op1.isChecked()||op2.isChecked()||op3.isChecked()||op4.isChecked()) {
                         turns++;
                         checked = (RadioButton) findViewById(hard_rg.getCheckedRadioButtonId());

                         if (hard_bank.get(currentQ).getAns() == checked.getText()) {
                             score += 10;
                             checkAns.add(1);
                         }else{
                             checkAns.add(0);

                         }

                         questions.add(hard_bank.get(currentQ).getQ());
                         answers.add(hard_bank.get(currentQ).getAns());

                         hard_rg.clearCheck();
                         currentQ++;

                         if (10 > turns) {
                             question_view.setText(hard_bank.get(currentQ).getQ());

                             op1.setText(hard_bank.get(currentQ).getOp1());
                             op2.setText(hard_bank.get(currentQ).getOp2());
                             op3.setText(hard_bank.get(currentQ).getOp3());
                             op4.setText(hard_bank.get(currentQ).getOp4());
                        }else {
                             turns++;
                             new AlertDialog.Builder(Hard.this)
                                     .setTitle("Done!!!")
                                     .setMessage("You have answered all the questions.")
                                     .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                             Intent i = new Intent(Hard.this,Score.class);
                                             i.putExtra(new Score().Score_key, score);
                                             i.putExtra(new Score().Level_key, 2);
                                             i.putExtra(H_Result_Questions, questions);
                                             i.putExtra(H_Result_Answers, answers);
                                             i.putExtra(H_Check_Ans, checkAns);
                                             i.putExtra("Total no. of questions attemped", turns);
                                             startActivity(i);
                                             Hard.this.finish();
                                         }
                                     }).show();
                         }
                     }
                         else{
                         Toast.makeText(Hard.this, "Select an option to proceed",Toast.LENGTH_SHORT).show();
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
