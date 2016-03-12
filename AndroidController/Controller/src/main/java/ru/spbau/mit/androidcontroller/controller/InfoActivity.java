package ru.spbau.mit.androidcontroller.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView mTextView;
    static final String inform = "information";
    public static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mTextView = (TextView) findViewById(R.id.textViewInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();  //hide action bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MainActivity.onResume()");
            throw e;
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.getString(inform) != null) {
            mTextView.setText(extras.getString(inform));
        } else {
            mTextView.setText("Error");
        }
    }

    public void buttonInfoClick(View view) {
        finish();
    }
}
