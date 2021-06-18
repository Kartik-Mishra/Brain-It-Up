package com.itrio.iqtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


public class Settings extends PreferenceActivity {


    MainMenu m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        m= new MainMenu();
        final Preference dialog = (Preference) findPreference("chinfo");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dialog();
                return false;
            }
        });
    }
    public void dialog()
    {
        m.change_info(Settings.this);

    }
    static boolean darkTheme(Context context)
    {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("theme",true);
    }

    static String title_Color(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("color","1");
    }

    static boolean awake(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("awake",true);
    }
    static boolean music(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("music",true);
    }

    static boolean vibrate(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("vibrate",true);
    }

    public class MyPreferenceCategory extends PreferenceCategory {
        public MyPreferenceCategory(Context context) {
            super(context);
        }

        public MyPreferenceCategory(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyPreferenceCategory(Context context, AttributeSet attrs,
                                    int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onBindView(View view) {
            super.onBindView(view);
            TextView titleView = (TextView) view.findViewById(android.R.id.title);
            titleView.setTextColor(Color.RED);
        }
    }

}
