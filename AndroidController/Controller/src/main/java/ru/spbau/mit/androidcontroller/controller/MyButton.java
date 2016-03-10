package ru.spbau.mit.androidcontroller.controller;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MyButton extends FrameLayout implements View.OnTouchListener, Settingable{
    private static final String TAG = SettingsActivity.class.getSimpleName();
    Button button;
    Integer[] settings;
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
        if (!MainActivity.isStart) return false;
        Log.d(MainActivity.TAG, "Touched!");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, settings[0].toString());
            MainActivity.send(settings[0]);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, settings[1].toString());
            MainActivity.send(settings[1]);
        }
        return true;
    }

    @Override
    public String getLabel(int counter) {
        if (button.getText() != null && button.getText().toString() != "") {
            return button.getText().toString();
        } else return  ("" + counter);
    }

    @Override
    public void setSettings(Integer[] settings) {
        Log.d(MainActivity.TAG, "Setted!");
        this.settings = settings.clone();
    }
}
