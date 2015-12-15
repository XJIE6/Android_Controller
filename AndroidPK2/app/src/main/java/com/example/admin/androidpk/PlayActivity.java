package com.example.admin.androidpk;

import android.app.Activity;
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

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMainActivity");
        /*TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setOnTouchListener(this);*/

        Intent curIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setContentView(extras.getInt(LAYOUT_KEY));
        } else {
            setContentView(R.layout.invalid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    public void spaceOnClick(View view) {
        Log.d(TAG, "onClickSpace");
    }

    public void up(View view) {
        MainActivity.send(0);
    }
    public void down(View view) {
        MainActivity.send(1);
    }
    public void left(View view) {
        MainActivity.send(2);
    }
    public void right(View view) {
        MainActivity.send(3);
    }


    public void ufire(View view) {
        MainActivity.send(4);
    }
    public void dfire(View view) {
        MainActivity.send(5);
    }
    public void lfire(View view) {
        MainActivity.send(6);
    }
    public void rfire(View view) {
        MainActivity.send(7);
    }


    public void bomb(View view) {
        MainActivity.send(8);
    }
    public void card(View view) {
        MainActivity.send(9);
    }
    public void space(View view) {
        MainActivity.send(10);
    }

}
