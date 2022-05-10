package com.twl.myapplication.night;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.twl.myapplication.R;

/**
 * @author tany
 */
public class NightActivity extends AppCompatActivity {

    private Application app;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this.getApplication();
    }

    private Application getApp() {
        return app;
    }

    private void testColorParse() {
        int colorValue = Color.parseColor("#FF00ff");
    }

    private void callReturnApp() {
        //测试 通过方法返回一个context对象，实际是Application，且非连续调用
        //应该报错
        Resources getAppM = getApp().getResources();
        doNothing();
        getAppM.getLayout(R.layout.activity_main);
        LayoutInflater.from(getApp());


        Application application = getApp();
        Resources getAppA = application.getResources();
        getAppA.getLayout(R.layout.activity_main);
        LayoutInflater.from(application);

        //activity实例 强转Context会误报
        Context activityContext = activity;
        Resources getActContext = activityContext.getResources();
        doNothing();
        getActContext.getLayout(R.layout.activity_main);
        LayoutInflater.from(activityContext);
    }

    private void call() {
        //测试 activity 非连续调用，不应该报错
        Resources activityResources = activity.getResources();
        doNothing();
        activityResources.getLayout(R.layout.activity_main);
    }

    private void callApp() {
        //测试 app 非连续调用,应该报错
        Resources activityResources = app.getResources();
        doNothing();
        activityResources.getLayout(R.layout.activity_main);
    }




    private void callAppString() {
        //测试 Application获取 string，不应该报错
        Resources activityResources = app.getResources();
        doNothing();
        activityResources.getString(R.string.app_name);
    }

    private void testThemeContext() {
        //测试 Activity 调用以下方法 不应报错
        activity.getResources().getString(R.string.app_name);
        activity.getResources().getColor(R.color.colorAccent);
        activity.getResources().getDrawable(R.drawable.ic_launcher_background);
        activity.getResources().getLayout(R.layout.activity_main);

        LayoutInflater.from(app);
        View.inflate(app, R.layout.activity_main, null);

        //测试 Activity 调用以下方法，不应该报错
        LayoutInflater.from(activity);
        View.inflate(activity, R.layout.activity_main, null);
        ContextCompat.getColor(activity, R.color.colorAccent);
        ContextCompat.getDrawable(activity, R.drawable.ic_launcher_background);
    }

    private void testApp() {
        //测试 app 调用以下方法，应该报错
        app.getResources().getColor(R.color.colorAccent);
        app.getResources().getDrawable(R.drawable.ic_launcher_background);
        app.getResources().getLayout(R.layout.activity_main);
        //忽略，为了除了非连续调用，这里的getString因为上面 调用 getLayout而误伤，改掉上面后不再报错
        app.getResources().getString(R.string.app_name);

        LayoutInflater.from(activity);
        View.inflate(activity, R.layout.activity_main, null);

        //测试 app 调用以下方法，应该报错
        LayoutInflater.from(app);
        View.inflate(app, R.layout.activity_main, null);
        ContextCompat.getColor(app, R.color.colorAccent);
        ContextCompat.getDrawable(app, R.drawable.ic_launcher_background);
    }

    private void doNothing() {

    }
}
