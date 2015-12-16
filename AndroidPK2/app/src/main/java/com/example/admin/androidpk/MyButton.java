package com.example.admin.androidpk;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by urijkravcenko on 14/12/15.
 */
public class MyButton extends FrameLayout implements View.OnTouchListener, Settingable{
    Button button;
    int[] settings;
    public MyButton(Context context) throws Exception {
        super(context);
        throw new Exception("Wrong button constructor");
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        button = new Button(context, attrs);
        addView(button);
        button.setOnTouchListener(this);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        throw new Exception("Wrong button constructor");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d(MainActivity.TAG, "Touched!");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            MainActivity.send(settings[0]);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            MainActivity.send(settings[1]);
        }
        return true;
    }

    @Override
    public void setSettings(int[] settings) {
        Log.d(MainActivity.TAG, "Setted!");
        this.settings = settings;
    }
}
