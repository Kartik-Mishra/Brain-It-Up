package com.itrio.iqtest;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Admin on 12-Jun-17.
 */

public class Music {
    static MediaPlayer mp ;
    /** Stop old song and start new one */
    public static void play(Context context,int resource)

    {
        stop(context);
        mp= MediaPlayer.create(context,resource);
        mp.setLooping(true);
        mp.start();
    }

    public void pause(){
        mp.pause();
    }
    public void resume(){
        mp.start();
    }
    /**  Stop the music */

    public  static  void  stop(Context context)
    {
        if(mp != null)
        {
            mp.stop();
            mp.release();
            mp=null;
        }
    }
}
