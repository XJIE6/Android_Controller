package com.example.admin.androidpk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Admin on 17.10.2015.
 */
public class InfoActivity extends AppCompatActivity {
    TextView mTextView;
    static final String inform = "information";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mTextView = (TextView) findViewById(R.id.textViewInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.getString(inform) != null) {
               mTextView.setText(extras.getString(inform));
        } else {
            mTextView.setText("Error");
        }
    }
}
