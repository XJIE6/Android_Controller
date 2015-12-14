package com.example.admin.androidpk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * Created by urijkravcenko on 14/12/15.
 */
public class MySampleButton extends FrameLayout implements View.OnTouchListener{
    Button button;
    public MySampleButton(Context context) {
        super(context);
    }

    public MySampleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        button = new Button(context, attrs);
        addView(button);
        button.setOnTouchListener(this);
    }

    public MySampleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            MainActivity.send(14);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            MainActivity.send(14 + 19);
        }
        return true;
    }
}