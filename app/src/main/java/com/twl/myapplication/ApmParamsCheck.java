package com.twl.myapplication;


import com.exam.apm.event.ApmAnalyzer;

public class ApmParamsCheck {
    public void test1() {
        String TXT = "p";
        new ApmAnalyzer().param("p", "abc");
        new ApmAnalyzer().param(TXT, "abc");
        new ApmAnalyzer().param("p2", "abc");

        String TXT2 = "P";
        new ApmAnalyzer().param("p3", "abc");
        new ApmAnalyzer().param("P", "abc");
        new ApmAnalyzer().param(TXT2, "abc");
    }
}
