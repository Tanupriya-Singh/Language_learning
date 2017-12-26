package com.example.android.miwok;

/**
 * Created by Tanupriya on 10-Nov-17.
 */

public class ReportCard {

    String grade = "";
    //Can be called to set the grade of a particular subject by passing the marks.
    //and the grade is returned;

    public String subjectMarks(int marks){
        if(marks >90 && marks<=100)grade = "S";
        if(marks >80 && marks<=90)grade = "A";
        if(marks >70 && marks<=80)grade = "B";
        if(marks >60 && marks<=70)grade = "C";
        if(marks >50 && marks<=60)grade = "D";
        if(marks >40 && marks<=50)grade = "E";
        return grade;
    }
    public String printReportCard(String name,String sub1 ,int marks1, String sub2 ,int marks2){
        return "Report card for candidate"+name+"is:\n"+sub1 + subjectMarks(marks1)+ sub2 + subjectMarks(marks2);
    }

}
