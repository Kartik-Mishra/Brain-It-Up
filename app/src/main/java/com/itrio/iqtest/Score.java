package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.vungle.publisher.AdConfig;
import com.vungle.publisher.Orientation;
import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.itrio.iqtest.Highscores.gen_score;
import static com.itrio.iqtest.Highscores.hard_score;
import static com.itrio.iqtest.Highscores.level_gen;
import static com.itrio.iqtest.Highscores.level_hard;
import static com.itrio.iqtest.Highscores.level_nor;
import static com.itrio.iqtest.Highscores.nor_score;

public class Score extends AppCompatActivity {


        int score,count;
        int level;
        TextView t, sar;
        //MainActivity m;

        final String app_id = "592e992120a4b6b076000ce8";
        final String placementID[] = {"MAINMEN87596"};
        String showAnsplacementID = "MAINMEN87596";
        VunglePub vunglePub =  VunglePub.getInstance() ;
        boolean showAnswers = false;
        public int def;
        static String KEY1 = "Score_e";
        static String KEY2 = "Score_n";
        static String KEY3 = "Score_h";
        static String KEY4 = "Score_g";
        final static public String nkey1="hi1";
        final static public String nkey2="hi2";

        final static public String nkey3="hi3";

        final static public String nkey4="hi4";

        Button mShowAnswers;
        public String Score_key = "score";
        ArrayList<String> ques,ans;
        ArrayList<Integer> check;
        public static final String Key_Ques_Res = "display questions result";
        public static final String Key_Ans_Res = "display answers result";
        public static final String Key_check_show = "display correct or incorrect";
        public String Level_key = "level";

        String[] sarcasm = {
                "How slow you are...I thought you were dead!",
                "Whaoooo.......How can a person be so silly!",
                "Let's try and guess your IQ. Surely it's a no. between 1 and 10"

        };
        private final VungleAdEventListener eventListener = new VungleAdEventListener() {
            @Override
            public void onAdEnd(@NonNull String s, boolean b, boolean b1) {

                if (b){
                    Log.i("Called","ad");
                    showAnswers = true;
                }

            }
            @Override
            public void onAdStart(@NonNull String s) {

            }

            @Override
            public void onUnableToPlayAd(@NonNull String s, String s1) {
                new AlertDialog.Builder(Score.this)
                        .setTitle("Unable to play ad")
                        .setMessage("Sorry, there's a problem in playing ad. Please check you internet connection.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }

            @Override
            public void onAdAvailabilityUpdate(@NonNull String s, boolean b) {
                Toast.makeText(Score.this,"An ad is available and ready to play",Toast.LENGTH_LONG).show();

            }

        };



        @Override
        public void onBackPressed() {
            super.onBackPressed();
             AlertDialog.Builder warn = new AlertDialog.Builder(Score.this);


                    warn.setMessage("Do you want to return to mainmenu?")
                            .setTitle("Confirm exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Score.this,MainMenu.class);
                            startActivity(i);
                            Score.this.finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

        }
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            //Changes 'back' button action
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder warn = new AlertDialog.Builder(Score.this);


                warn.setMessage("Do you want to return to mainmenu?")
                        .setTitle("Confirm exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Score.this,MainMenu.class);
                                startActivity(i);
                                Score.this.finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
            return true;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score);

           vunglePub.init(Score.this, app_id, placementID, new VungleInitListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
           vunglePub.clearAndSetEventListeners(eventListener);
           vunglePub.loadAd(showAnsplacementID);

            score = getIntent().getExtras().getInt(Score_key);
            level = getIntent().getExtras().getInt(Level_key);
            t = (TextView)findViewById(R.id.score);
            sar=(TextView)findViewById(R.id.sar);
            t.setText(Integer.toString(score));
            checkScore( level , score);

            final SharedPreferences preferences = getSharedPreferences("Answers",0);
            //showAnswers = preferences.getBoolean(showAns,false);

            mShowAnswers = (Button) findViewById(R.id.show_answers);
            mShowAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (showAnswers) {
                        if (level == 0) {
                            ques = getIntent().getStringArrayListExtra(new Easy().E_Result_Questions);
                            count = getIntent().getIntExtra("Total no. of questions attemped",0);
                            ans = getIntent().getStringArrayListExtra(new Easy().E_Result_Answers);
                            check = getIntent().getIntegerArrayListExtra(new Easy().E_Check_Ans);
                            Intent show = new Intent(Score.this, Result_QA.class);
                            show.putExtra(Key_Ques_Res, ques);
                            show.putExtra("GTQ",count);         //GTQ = Get total questions
                            show.putExtra(Key_Ans_Res, ans);
                            show.putExtra(Key_check_show, check);
                            startActivity(show);

                        } else if (level == 1) {

                            ques = getIntent().getStringArrayListExtra(new Normal().N_Result_Questions);
                            count = getIntent().getIntExtra("Total no. of questions attemped",0);
                            ans = getIntent().getStringArrayListExtra(new Normal().N_Result_Answers);
                            check = getIntent().getIntegerArrayListExtra(new Normal().N_Check_Ans);
                            Intent show = new Intent(Score.this, Result_QA.class);
                            show.putExtra(Key_Ques_Res, ques);
                            show.putExtra("GTQ",count);         //GTQ = Get total questions
                            show.putExtra(Key_check_show, check);
                            show.putExtra(Key_Ans_Res, ans);
                            startActivity(show);

                        } else if (level == 2) {
                            ques = getIntent().getStringArrayListExtra(new Hard().H_Result_Questions);
                            ans = getIntent().getStringArrayListExtra(new Hard().H_Result_Answers);
                            check = getIntent().getIntegerArrayListExtra(new Hard().H_Check_Ans);
                            count = getIntent().getIntExtra("Total no. of questions attemped",0);
                            Intent show = new Intent(Score.this, Result_QA.class);
                            show.putExtra(Key_Ques_Res, ques);
                            show.putExtra("GTQ", count);         //GTQ = Get total questions
                            show.putExtra(Key_Ans_Res, ans);
                            show.putExtra(Key_check_show, check);
                            startActivity(show);


                        } else if (level == 3) {
                            ques = getIntent().getStringArrayListExtra(new Genius().G_Result_Questions);
                            ans = getIntent().getStringArrayListExtra(new Genius().G_Result_Answers);
                            count = getIntent().getIntExtra("Total no. of questions attemped",0);
                            check = getIntent().getIntegerArrayListExtra(new Genius().G_Check_Ans);
                            Intent show = new Intent(Score.this, Result_QA.class);
                            show.putExtra(Key_Ques_Res, ques);
                            show.putExtra("GTQ",count);         //GTQ = Get total questions
                            show.putExtra(Key_Ans_Res, ans);
                            show.putExtra(Key_check_show, check);
                            startActivity(show);


                        } else {

                        }
                    }else{
                        new AlertDialog.Builder(Score.this)
                                .setTitle("Access answers")
                                .setMessage("Watch an ad to see answers.")
                                .setPositiveButton("watch", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watch();
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }
            });
            unlocklevels();
        }
        @Override
        protected void onPause() {
            super.onPause();
            vunglePub.onPause();
        }


        public void watch(){

             AdConfig globalAdConfig =  vunglePub.getGlobalAdConfig();
            globalAdConfig.setSoundEnabled(true);
            globalAdConfig.setOrientation(Orientation.autoRotate);
             if (vunglePub.isAdPlayable(showAnsplacementID)) {
                 vunglePub.playAd(showAnsplacementID, globalAdConfig);
             }else {
                 Toast.makeText(this, "Loading ad...",Toast.LENGTH_LONG).show();
             }
        }


        public void setsarcasm(boolean type) {
            if (type) {
                sar.setText("I think it's the biggest achievement for you.");
            }
            if (!type) {
                Random r = new Random();
                int i;
                i = r.nextInt(3);
                sar.setText(sarcasm[i]);
            }
        }
        public  void checkScore(int level,int score)
        {

            int def_score =50;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            if(level == 0)
            {  def_score =Integer.parseInt( preferences.getString(KEY1 , "0"));
                if(score>def_score)
                {
                    setsarcasm(true);
                    setDefaults(KEY1,  Integer.toString(score),nkey1,new MainMenu().getAccName(this),this);
                }
                else
                    setsarcasm(false);
            }
            else if(level == 1)
            {   def_score =Integer.parseInt( preferences.getString(KEY2 , "0"));
                if(score>def_score)
                {
                    setsarcasm(true);
                    setDefaults(KEY2,  Integer.toString(score),nkey2,new MainMenu().getAccName(this),this);
                }
                else
                    setsarcasm(false);
            }
            else if (level  == 2)
            {   def_score =Integer.parseInt( preferences.getString(KEY3 , "0"));
                if(score>def_score)
                {
                    SharedPreferences info = getSharedPreferences("information", Context.MODE_PRIVATE);

                    setsarcasm(true);
                    setDefaults(KEY3,  Integer.toString(score),nkey3,new MainMenu().getAccName(this),this);
                }
                else
                    setsarcasm(false);
            }
            else
            {        def_score =Integer.parseInt( preferences.getString(KEY4 , "0"));
                if(score>def_score)
                {
                    setsarcasm(true);
                    setDefaults(KEY4,  Integer.toString(score),nkey4,new MainMenu().getAccName(this),this);
                }
                else
                    setsarcasm(false);
            }



        }

        public static void setDefaults(String key, String  value,String name_key,String playername, Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.apply();

            SharedPreferences name = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit1 = name.edit();
            edit1.putString(name_key ,playername );
            edit1.apply();
        }



        public static String getDefaults_easy( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(KEY1 , "0");
        }

        public static String getDefaults_nor( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(KEY2 , "0");
        }

        public static String getDefaults_hard( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(KEY3 , "0");
        }

        public static String getDefaults_gen( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(KEY4 , "0");
        }

        public static String getName_easy( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(nkey1 , "");
        }

        public static String getName_nor( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(nkey2 , "");
        }

        public static String getName_hard( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(nkey3 , "");
        }

        public static String getName_gen( Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(nkey4 , "");
        }


        private void unlocklevels()
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            //SharedPreferences.Editor editor = prefs.edit();
            if (true)
                if(Integer.parseInt(getDefaults_easy(this))>= nor_score)
                {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_nor,true);
                    edito.apply();
                }

            if (true)
                if(Integer.parseInt(getDefaults_nor(this)) >= hard_score)
                {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_hard,true);
                    edito.apply();
                }

            if (true)
                if( Integer.parseInt(getDefaults_hard(this))>= gen_score)
                {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_gen,true);
                    edito.apply();
                }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            vunglePub.clearEventListeners();

        };

        @Override
        protected void onResume() {
            super.onResume();
            unlocklevels();
            vunglePub.onResume();
        }

}

