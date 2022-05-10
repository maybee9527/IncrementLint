package com.exam.annotations;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestJetAnnotation {
    private String content = "代码里面的AppName";
    private String content1 = "代码里面";
    private String content2 = "代码里面的AppName";

    @Nullable
    public String getClassInfo() {
        return null;
    }


    @NotNull
    public String getClassTxt() {
        return null;
    }


    public void setClassTxt(@Nullable String name) {
        this.content = name;
    }
    public void setClassInfo(@NotNull String info) {
        this.content = info;
    }
}
