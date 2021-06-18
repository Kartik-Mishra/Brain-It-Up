package com.itrio.iqtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.Orientation;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.itrio.iqtest.Highscores.level_gen;
import static com.itrio.iqtest.Highscores.level_hard;
import static com.itrio.iqtest.Highscores.level_nor;
import static java.util.logging.Level.ALL;

public class MainMenu extends AppCompatActivity {

    Button mStart, mHighscore, mExit, mTips;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    boolean unlock_normal = false;
    boolean unlock_hard = false;
    boolean unlock_genius = false;

    TextView acc_name;
    ImageView acc_img;

    String showAns = "Show Answers to user";

    final String app_id = "592e992120a4b6b076000ce8";
    final String placementID[] = {"MAINMEN87596"};

    VunglePub vunglePub =  VunglePub.getInstance() ;
    private GoogleApiClient client;
    private final VungleAdEventListener eventListener = new VungleAdEventListener() {
        @Override
        public void onAdEnd(@NonNull String s, boolean b, boolean b1) {
            Log.i("Call","hua");
            if(b){
                if(unlock_normal){
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_nor,true);
                    edito.apply();
                }
                else if (unlock_hard){
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_hard,true);
                    edito.apply();
                }
                else if (unlock_genius){
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
                    SharedPreferences.Editor edito = pref.edit();
                    edito.putBoolean(level_gen,true);
                    edito.apply();
                }
            }
        }

        @Override
        public void onAdStart(@NonNull String s) {

        }

        @Override
        public void onUnableToPlayAd(@NonNull String s, String s1) {
            new AlertDialog.Builder(MainMenu.this)
                    .setTitle("Unable to play Ad")
                    .setMessage("Sorry, there's a problem in playing ad. Please check you internet connection.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }

        @Override
        public void onAdAvailabilityUpdate(@NonNull String s, boolean b) {

        }

    };

    public void unlockLevel(){
        if(unlock_normal){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edito = pref.edit();
            edito.putBoolean(level_nor,true);
            edito.apply();
        }
        else if (unlock_hard){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edito = pref.edit();
            edito.putBoolean(level_hard,true);
            edito.apply();
        }
        else if(unlock_genius){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edito = pref.edit();
            edito.putBoolean(level_gen,true);
            edito.apply();
        }
        else{

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        vunglePub.init(this, app_id, placementID, new VungleInitListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        vunglePub.clearAndSetEventListeners(eventListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        acc_img = (ImageView) findViewById(R.id.acc_img);
        acc_name = (TextView) findViewById(R.id.account_name);

        if(getAccGender()){
            acc_img.setImageResource(R.drawable.male);
        }else{
            acc_img.setImageResource(R.drawable.female);
        }

        acc_name.setText(getAccName());

        mStart = (Button) findViewById(R.id.start_button);
        mHighscore = (Button) findViewById(R.id.highscore_button);
        mExit = (Button) findViewById(R.id.exit_button);
        mTips = (Button) findViewById(R.id.tips_button);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  openDifficuiltyDialog();

               openDifficuiltyDialog();

            }
        });

        mHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainMenu.this,Highscores.class));
            }
        });

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //finishAffinity();
                // System.exit(0);
                Log.i("Start", "Aplication clicked Exit");

            }
        });

        mTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Tips.class));

            }
        });

        acc_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_info(MainMenu.this);
            }
        });
        acc_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_info(MainMenu.this);
            }
        });


    }

    public void change_info(Context con){
        Dialog ci = new ChangeInfo(con);
        ci.setCancelable(false);
        ci.setCanceledOnTouchOutside(false);
        ci.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //setResult(RESULT_CLOSE ALL);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.finish();
          //  android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(0);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(MainMenu.this, com.itrio.iqtest.Settings.class));
                break;

            case R.id.ref:

                recreate();
                break;

            case R.id.about_us:
                //vunglePub.playAd();
                startActivity(new Intent(MainMenu.this, About_us.class));
                break;
            case R.id.share:
                shareApp();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void openDifficuiltyDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.difficuilty_title)
                .setItems(R.array.difficuilty, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int diff) {
                        getDifficuilty(diff);
                    }
                }).show();

    }

    public void getDifficuilty(int diff_level) {


        if (diff_level == 0) {
            Intent easy = new Intent(MainMenu.this, Easy.class);
            startActivity(easy);
            MainMenu.this.finish();
        } else if (diff_level == 1) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
            if (sp.getBoolean(level_nor, false)){
                Intent normal = new Intent(MainMenu.this, Normal.class);
                startActivity(normal);
                MainMenu.this.finish();
            }else {
                new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Content is Locked")
                        .setMessage("This difficuilty is locked. Score 50 in Easy to unlock. Tap unlock to skip this process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Unlock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(MainMenu.this)
                                        .setTitle("Unlock content")
                                        .setMessage("Watch an ad to unlock this content")
                                        .setPositiveButton("watch", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                watch(1);
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                        }).show();

            }
        } else if (diff_level == 2) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
            if(sp.getBoolean(new Highscores().level_hard,false)) {

                Intent hard = new Intent(MainMenu.this, Hard.class);
                startActivity(hard);
                MainMenu.this.finish();
            }else{
                new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Content is Locked")
                        .setMessage("This difficuilty is locked. Score 50 in Normal to unlock. Tap unlock to skip this process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Unlock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(MainMenu.this)
                                        .setTitle("Unlock content")
                                        .setMessage("Watch an ad to unlock this content")
                                        .setPositiveButton("watch", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                watch(2);
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                        }).show();
            }
        } else if (diff_level == 3) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);

            if(sp.getBoolean(new Highscores().level_gen,false)) {
                Intent genius = new Intent(MainMenu.this, Genius.class);
                startActivity(genius);
                MainMenu.this.finish();
            }else{
                new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Content is Locked")
                        .setMessage("This difficuilty is locked. Score 50 in Hard to unlock. Tap unlock to skip this process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Unlock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(MainMenu.this)
                                        .setTitle("Unlock content")
                                        .setMessage("Watch an ad to unlock this content")
                                        .setPositiveButton("watch", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                watch(3);
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                        }).show();
                }

            }
        }
    public void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }



    public  void watch(int diff){
        if (diff == 1){
            unlock_normal = true;
        }
        else if (diff == 2){
            unlock_hard = true;
        }
        else if(diff == 3){
            unlock_genius = true;
        }
        String mainmenuPlacementID = "MAINMEN87596";
        AdConfig globalAdConfig =  vunglePub.getGlobalAdConfig();
        globalAdConfig.setSoundEnabled(true);
        globalAdConfig.setOrientation(Orientation.autoRotate);
        vunglePub.loadAd(mainmenuPlacementID);
        if (vunglePub.isAdPlayable(mainmenuPlacementID)) {
            vunglePub.playAd(mainmenuPlacementID, globalAdConfig);
        }else{
            Toast.makeText(this, "Loading ad... Try again after few seconds.",Toast.LENGTH_LONG).show();
            //watch(diff);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public String getAccName(){
        SharedPreferences info = getSharedPreferences("information", Context.MODE_PRIVATE);

        return info.getString(new UserInfo().KEY_USERNAME, "") ;
    }
    public String getAccName(Context con){
        SharedPreferences info = con.getSharedPreferences("information", Context.MODE_PRIVATE);

        return info.getString(new UserInfo().KEY_USERNAME, "") ;
    }
    public Boolean getAccGender(){
        SharedPreferences info = getSharedPreferences("information", Context.MODE_PRIVATE);
        return info.getBoolean("gender",true);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    protected void onPause() {
        super.onPause();
        vunglePub.onPause();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        vunglePub.clearEventListeners();

    };


    @Override
    protected void onResume() {
        super.onResume();

        vunglePub.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.

    }
}

