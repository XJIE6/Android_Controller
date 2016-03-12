package ru.spbau.mit.androidcontroller.controller;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
