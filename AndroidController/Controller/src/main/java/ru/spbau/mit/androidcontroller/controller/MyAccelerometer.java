package ru.spbau.mit.androidcontroller.controller;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class MyAccelerometer extends FrameLayout implements SensorEventListener, Settingable {

    private final SensorManager msensorManager; //Менеджер сенсоров аппрата

    int prevComm = -1;
    Integer[] settings;

    private float[] rotationMatrix;     //Матрица поворота
    private float[] accelData;           //Данные с акселерометра
    private float[] magnetData;       //Данные геомагнитного датчика
    private float[] OrientationData; //Матрица положения в пространстве

    public MyAccelerometer(Context context) throws Exception {
        super(context);
        throw new Exception("Wrong accelerometer constructor");
    }

    public MyAccelerometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        msensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        rotationMatrix = new float[16];
        accelData = new float[3];
        magnetData = new float[3];
        OrientationData = new float[3];
        msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);

    }

    public MyAccelerometer(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        throw new Exception("Wrong accelerometer constructor");
    }

    private void loadNewSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //Определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //Если акселерометр
            accelData = event.values.clone();
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //Если геомагнитный датчик
            magnetData = event.values.clone();
        }
    }

    int getComm(int a) {
        if (a >= 10) {
            return 1;
        }
        if (a <= -10) {
            return 2;
        }
        return 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!MainActivity.isStart) return;
        if (this != SettingsActivity.curAccelerometr) return;
        loadNewSensorData(event); // Получаем данные с датчика
        SensorManager.getRotationMatrix(rotationMatrix, null, accelData, magnetData); //Получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, OrientationData); //Получаем данные ориентации устройства в пространстве
        int curComm = getComm((int) Math.round(Math.toDegrees(OrientationData[1])));
        if (prevComm != curComm) {
            Log.d(MainActivity.TAG, "" + (prevComm + 1));
            if (prevComm != 0) {
                MainActivity.send(settings[prevComm + 1]);
            }
            prevComm = curComm;
            if (prevComm != 0) {
                MainActivity.send(settings[prevComm - 1]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void setSettings(Integer[] settings) {
        SettingsActivity.curAccelerometr = this;
        this.settings = new Integer[settings.length];
        for (int i = 0; i < settings.length; i++) {
            this.settings[i] = settings[i];
        }
    }

    @Override
    public String getLabel(int counter) {
        return ("" + counter);
    }
}