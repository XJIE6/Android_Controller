package ru.spbau.mit.androidcontroller.controller;


import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import ru.spbau.mit.androidcontroller.tools.Protocol;

public class PlayActivity extends AppCompatActivity{
    private static final String TAG = PlayActivity.class.getSimpleName();
    private static final String LAYOUT_KEY = "cur_layout_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreatePlayActivity");

        Intent curIntent = getIntent();       //take the choice and set as activity
        if (!curIntent.hasExtra(LAYOUT_KEY)) {
            setContentView(R.layout.invalid);
        } else {
            int layoutId = curIntent.getIntExtra(LAYOUT_KEY, 0);
            setContentView(layoutId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();  //hides action bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }

        try {
            SettingsActivity.setSettingsAndSendServer(this);  //sends to server all commands and etc
        } catch (NumberFormatException e) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra(InfoActivity.inform, "Wrong command format");
            startActivity(intent);
            finish();
        }
        MainActivity.isStart = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.send(Protocol.END_RUN_COMMAND);
        MainActivity.isStart = false;
    }
}
