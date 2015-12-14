package com.example.admin.androidpk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.zip.Inflater;

/**
 * Created by Admin on 17.10.2015.
 */
public class PlayActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LAYOUT_KEY = "cur_layout";
    private static final String OUT = "out_buffer";
    DataOutputStream out;

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void f(Integer s, int[] a) {
        ((Settingable) findViewById(getResources().getIdentifier("View" + s.toString(), "id", getPackageName()))).setSettings(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMainActivity34534645645645");
        /*TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setOnTouchListener(this);*/

        //Intent curIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "CreateMainActivity324");
        if (extras != null) {
            setContentView(extras.getInt(LAYOUT_KEY));
            //out = (DataOutputStream) extras.get(OUT);
        } else {
            setContentView(R.layout.invalid);
        }
        Log.d(TAG, "CreateMainActivity234234");

        f(1, new int[]{0, 1});
        f(2, new int[]{2, 3});
        f(3, new int[]{4, 5});
        f(4, new int[]{6, 7});
        f(5, new int[]{16, 17});
        f(6, new int[]{18, 19});
        f(7, new int[]{20, 21});
        f(8, new int[]{8, 9});
        f(9, new int[]{10, 11});
        f(10, new int[]{12, 13});
        f(11, new int[]{14, 15});
        System.out.print("wwwwwwwwwwwwwww");
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        System.out.print("eeeeeeeeeeeeee");
    }
}
