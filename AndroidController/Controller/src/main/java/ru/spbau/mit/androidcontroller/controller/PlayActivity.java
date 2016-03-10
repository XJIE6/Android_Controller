package ru.spbau.mit.androidcontroller.controller;


import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

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
