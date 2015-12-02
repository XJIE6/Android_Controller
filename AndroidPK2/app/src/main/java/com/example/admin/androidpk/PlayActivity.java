package com.example.admin.androidpk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMainActivity");

        Intent curIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setContentView(extras.getInt(LAYOUT_KEY));
            out = (DataOutputStream) extras.get(OUT);
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
        MainActivity.send("lol");
    }

}
