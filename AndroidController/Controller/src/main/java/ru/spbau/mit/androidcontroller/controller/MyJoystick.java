package ru.spbau.mit.androidcontroller.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zerokol.views.JoystickView;

public class MyJoystick extends FrameLayout implements JoystickView.OnJoystickMoveListener, Settingable {
    JoystickView joystick;
    int prevComm = -1;
    Integer[] settings;

    public MyJoystick(Context context) throws Exception {
        super(context);
        throw new Exception("Wrong joystick constructor");
    }

    public MyJoystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        joystick = new JoystickView(context, attrs);
        addView(joystick);
        joystick.setOnJoystickMoveListener(this, JoystickView.DEFAULT_LOOP_INTERVAL);
    }

    public MyJoystick(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        throw new Exception("Wrong joystick constructor");
    }

    private int push(int a) {
        return a - 1;
    }
    private int release(int a) {
        return a + 7;
    }

    @Override
    public void onValueChanged(int a, int aa, int curComm) {
        if (!MainActivity.isStart) return;
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
    public String getLabel(int counter) {
        return ("" + counter);
    }

    @Override
    public void setSettings(Integer[] settings) {
        this.settings = settings;
    }
}
