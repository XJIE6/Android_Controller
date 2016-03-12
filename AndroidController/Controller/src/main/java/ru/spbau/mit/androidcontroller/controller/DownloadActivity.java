package ru.spbau.mit.androidcontroller.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DownloadActivity extends AppCompatActivity {   //is showing the text "Downloading"
                                                            // while connection service is working
    static final String IP = "IP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
