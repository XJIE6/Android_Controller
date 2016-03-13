package ru.spbau.mit.androidcontroller.controller;


import android.view.View;

public class ListItem {
    String editText;
    String textView;
    View view;
    ListItem(String editText, String textView, View view) {
        this.editText = editText;
        this.textView = textView;
        this.view = view;
    }
}
