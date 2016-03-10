package ru.spbau.mit.androidcontroller.controller;


/**
 * Created by urijkravcenko on 14/12/15.
 */
public interface Settingable {
    void setSettings(Integer[] settings);
    String getLabel(int counter);
}
