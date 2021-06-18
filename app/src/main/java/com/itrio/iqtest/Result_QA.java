package com.itrio.iqtest;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.ListView;

import java.util.ArrayList;

public class Result_QA extends Activity {


    ListView result;
    ReslutListViewAdapter reslutListViewAdapter;
    ArrayList<String> ques, ans;
    ArrayList<Integer> check ;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__q);

        Bundle b= getIntent().getExtras();
        ques = b.getStringArrayList(new Score().Key_Ques_Res);
        ans = b.getStringArrayList(new Score().Key_Ans_Res);
        check = b.getIntegerArrayList(new Score().Key_check_show);
        count = b.getInt("GTQ");
        result = (ListView) findViewById(R.id.result_view);
        reslutListViewAdapter = new ReslutListViewAdapter(this, ques, ans, check, count);

       // System.out.println("adapter => "+reslutListViewAdapter.getCount());
        result.setAdapter(reslutListViewAdapter);
    }
}
