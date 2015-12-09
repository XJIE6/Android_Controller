package com.example.admin.androidpk;

import android.content.Context;
import android.os.Handler;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.zerokol.views.*;

/**
 * Created by urijkravcenko on 09/12/15.
 */
public class MySampleView extends FrameLayout implements JoystickView.OnJoystickMoveListener {
    JoystickView joystick;
    int k = -1;

    public MySampleView(Context context) {
        super(context);
    }

    public MySampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        joystick = new JoystickView(context, attrs);
        addView(joystick);
        joystick.setOnJoystickMoveListener(this, 20);
    }

    public MySampleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onValueChanged(int i, int i1, int i2) {
        switch (i2) {
            case JoystickView.FRONT:
                if (k != 0) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(0);
                    k = 0;
                }
                break;
            case JoystickView.FRONT_RIGHT:
                if (k != 1) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(1);
                    k = 1;
                }
                break;
            case JoystickView.RIGHT:
                if (k != 2) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(2);
                    k = 2;
                }
                break;
            case JoystickView.RIGHT_BOTTOM:
                if (k != 3) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(3);
                    k = 3;
                }
                break;
            case JoystickView.BOTTOM:
                if (k != 4) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(4);
                    k = 4;
                }
                break;
            case JoystickView.BOTTOM_LEFT:
                if (k != 5) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(5);
                    k = 5;
                }
                break;
            case JoystickView.LEFT:
                if (k != 6) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(6);
                    k = 6;
                }
                break;
            case JoystickView.LEFT_FRONT:
                if (k != 7) {
                    if (k != -1) {
                        MainActivity.send(k + 8);
                    }
                    MainActivity.send(7);
                    k = 7;
                }
                break;
            default:
                if (k != -1) {
                    MainActivity.send(k + 8);
                }
                k = -1;
        }
    }
}
