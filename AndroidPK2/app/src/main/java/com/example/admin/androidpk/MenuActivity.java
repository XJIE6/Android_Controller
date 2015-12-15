package com.example.admin.androidpk;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.List;
import java.util.Queue;

/**
 * Created by Admin on 16.10.2015.
 */
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FrameLayout mPreviewFrameLayout;
    LinearLayout mMenuChoiceLayout;
    int curSelection;

    private boolean setLayoutsChoice() {
        int count = 0;
        String curId = "id_choice_";
        while (++count > 0) {
            int rId = -1;
            curId += count;
            Class classRID = R.id.class;
            try {
                rId = classRID.getField(curId).;
            } catch (NoSuchFieldException e) {
                return false;
            }
            if (rId < 0)
                return false;


        }
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMainActivity");
        setContentView(R.layout.activity_menu);
        mPreviewFrameLayout = (FrameLayout) findViewById(R.id.preview_layout);
        mMenuChoiceLayout = (LinearLayout) findViewById(R.id.menu_choice_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        mPreviewFrameLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choice1, mPreviewFrameLayout);
        curSelection = R.layout.choice1;

        int countOfLayoutFiles = findLayoutsChoice();
        String name = "Mode "
        for (int i = 1; i <= countOfLayoutFiles; i++) {
            String curName = name + i;
            Button curButton = new Button(this);
            curButton.setText(curName);
            ViewGroup.LayoutParams paramsButton = new ViewGroup.LayoutParams(this, )
            mMenuChoiceLayout.addView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickA(View view) {
        Log.d(TAG, "onClickA");
        mPreviewFrameLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choice1, mPreviewFrameLayout);
        curSelection = R.layout.choice1;
    }

    public void onClickB(View view) {
        mPreviewFrameLayout.removeAllViews();
        Log.d(TAG, "onClickB");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choice2, mPreviewFrameLayout);
        curSelection = R.layout.choice2;
    }

    public void onClickC(View view) {
        mPreviewFrameLayout.removeAllViews();
        Log.d(TAG, "onClickC");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choice3, mPreviewFrameLayout);
        curSelection = R.layout.choice3;
    }

    public void menuButtonOk(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("cur_layout", curSelection);
        startActivity(intent);
    }


}
