package ru.spbau.mit.androidcontroller.controller;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListItem {
    LinearLayout ll;
    EditText editText;
    TextView textView;
    View view;
    ListItem(LinearLayout ll, EditText editText, TextView textView, View view) {
        this.ll = ll;
        this.editText = editText;
        this.textView = textView;
        this.view = view;
    }
}
