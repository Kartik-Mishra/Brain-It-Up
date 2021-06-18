package com.itrio.iqtest;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.itrio.iqtest.R.color.incorrect_back;


public class ReslutListViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<Integer> check;
    int count;

    public ReslutListViewAdapter(Activity con, ArrayList<String> ques, ArrayList<String> ans, ArrayList<Integer> c, int cnt){

        super();
        context = con;
        questions = ques;
        answers = ans;
        check = c;
        count = cnt;

    }

    @Override
    public int getCount() {
        return count;
    }
    private class ViewHolder {
        TextView mQ;
        TextView mA;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder;
            LayoutInflater inflater = context.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.result_listadapter, null);

                if (check.get(position) == 1) {
                    convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.correct_back));
                } else {
                    convertView.setBackgroundColor(ContextCompat.getColor(context, incorrect_back));
                }
                holder = new ViewHolder();
                holder.mQ = (TextView) convertView.findViewById(R.id.question_result);
                holder.mA = (TextView) convertView.findViewById(R.id.answer_result);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mQ.setText(questions.get(position));
            holder.mA.setText(answers.get(position));


        } catch (Exception e) {

        }
        return convertView;
    }
}
