package com.itrio.iqtest;



public class Genius_Questions {
   String question;
    String opt1, opt2,opt3,opt4;
    private String answer ;


    public  Genius_Questions(String ques, String op1, String op2, String op3, String op4, String ans){
        question = ques.trim();
        opt1 = op1.trim();
        opt2 = op2.trim();
        opt3 = op3.trim();
        opt4 = op4.trim();
        answer = ans.trim();

    }

    public String getQ() {
        return  question;
    }
    public String getOp1(){
        return opt1;
    }
    public String getOp2(){
        return opt2;
    }
    public String getOp3(){
        return opt3;
    }
    public String getOp4(){
        return opt4;
    }
    public String getAns(){
        return answer;
    }
}
