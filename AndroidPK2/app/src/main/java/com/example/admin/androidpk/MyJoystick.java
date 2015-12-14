package com.example.admin.androidpk;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zerokol.views.JoystickView;

/**
 * Created by urijkravcenko on 14/12/15.
 */
public class MyJoystick extends FrameLayout implements JoystickView.OnJoystickMoveListener, Settingable {
    JoystickView joystick;
    int prevComm = -1;
    int[] settings;

    public MyJoystick(Context context) {
        super(context);
    }

    public MyJoystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        joystick = new JoystickView(context, attrs);
        addView(joystick);
        joystick.setOnJoystickMoveListener(this, JoystickView.DEFAULT_LOOP_INTERVAL);
    }

    public MyJoystick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int push(int a) {
        return a - 1;
    }
    private int release(int a) {
        return a + 7;
    }

    @Override
    public void onValueChanged(int a, int aa, int curComm) {
        if (prevComm != curComm) {
            if (prevComm != 0) {
                MainActivity.send(settings[release(prevComm)]);
            }
            prevComm = curComm;
            if (prevComm != 0) {
                MainActivity.send(settings[push(prevComm)]);
            }
        }
    }

    @Override
    public void setSettings(int[] settings) {
        this.settings = settings;
    }
}
