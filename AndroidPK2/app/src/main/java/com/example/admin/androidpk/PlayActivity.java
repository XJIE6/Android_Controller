package com.example.admin.androidpk;

import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;

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

    private void f(Integer s, int[] a) {
        ((Settingable) findViewById(getResources().getIdentifier("View" + s.toString(), "id", getPackageName()))).setSettings(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreatePlayActivity");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setContentView(extras.getInt(LAYOUT_KEY));
        } else {
            setContentView(R.layout.invalid);
        }

        /*f(1, new int[]{0, 1});
        f(2, new int[]{2, 3});
        f(3, new int[]{4, 5});
        f(4, new int[]{6, 7});
        f(5, new int[]{16, 17});
        f(6, new int[]{18, 19});
        f(7, new int[]{20, 21});
        f(8, new int[]{8, 9});
        f(9, new int[]{10, 11});
        f(10, new int[]{12, 13});
        f(11, new int[]{14, 15});*/
        //f(100, new int[]{0, 22, 2, 24, 4, 26, 6, 28, 1, 23, 3, 25, 5, 27, 7, 29});
        f(100, new int[]{0, 2, 1, 3});
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
    }
}
