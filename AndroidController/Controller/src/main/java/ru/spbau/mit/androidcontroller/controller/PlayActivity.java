package ru.spbau.mit.androidcontroller.controller;


import android.app.Application;
import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by Admin on 17.10.2015.
 */
public class PlayActivity extends AppCompatActivity{
    private static final String TAG = PlayActivity.class.getSimpleName();
    private ViewGroup llayout = null;
    private static final String LAYOUT_KEY = "cur_layout_id";
    //private static final String CHOICE_KEY = "number_choice";

 /*   public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setSettings(Integer s, Integer[] a) {
        ((Settingable) findViewById(getResources().getIdentifier("View" + s.toString(), "id", getPackageName()))).setSettings(a);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreatePlayActivity");

        Intent curIntent = getIntent();
        if (!curIntent.hasExtra(LAYOUT_KEY)) {
            setContentView(R.layout.invalid);
        } else {
            int layoutId = curIntent.getIntExtra(LAYOUT_KEY, 0);
            setContentView(layoutId);
        }

        /*setSettings(1, new int[]{0, 1});
        setSettings(2, new int[]{2, 3});
        setSettings(3, new int[]{4, 5});
        setSettings(4, new int[]{6, 7});
        setSettings(5, new int[]{16, 17});
        setSettings(6, new int[]{18, 19});
        setSettings(7, new int[]{20, 21});
        setSettings(8, new int[]{8, 9});
        setSettings(9, new int[]{10, 11});
        setSettings(10, new int[]{12, 13});
        setSettings(11, new int[]{14, 15});*/
        //setSettings(100, new int[]{0, 22, 2, 24, 4, 26, 6, 28, 1, 23, 3, 25, 5, 27, 7, 29});
/*        try {
            UtilsChoice utils = new UtilsChoice(curChoice, 1);
            setSettings(1, (Integer[]) utils.readCommandsFromFileAndSendServer().get(0).toArray());
        } catch (Exception e) {
            Log.d(TAG, "Can't to get current choice.");
            e.printStackTrace();
        } */
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }

        SettingsActivity.setSettingsAndSendServer(this);
        MainActivity.isStart = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.send(-1);
        MainActivity.isStart = false;
    }
}
