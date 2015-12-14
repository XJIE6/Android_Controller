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
                if (k != 11) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(11);
                    k = 11;
                }
                break;
            case JoystickView.FRONT_RIGHT:
                if (k != 12) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(12);
                    k = 12;
                }
                break;
            case JoystickView.RIGHT:
                if (k != 13) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(13);
                    k = 13;
                }
                break;
            case JoystickView.RIGHT_BOTTOM:
                if (k != 14) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(14);
                    k = 14;
                }
                break;
            case JoystickView.BOTTOM:
                if (k != 15) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(15);
                    k = 15;
                }
                break;
            case JoystickView.BOTTOM_LEFT:
                if (k != 16) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(16);
                    k = 16;
                }
                break;
            case JoystickView.LEFT:
                if (k != 17) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(17);
                    k = 17;
                }
                break;
            case JoystickView.LEFT_FRONT:
                if (k != 18) {
                    if (k != -1) {
                        MainActivity.send(k + 19);
                    }
                    MainActivity.send(18);
                    k = 18;
                }
                break;
            default:
                if (k != -1) {
                    MainActivity.send(k + 19);
                }
                k = -1;
        }
    }
}
