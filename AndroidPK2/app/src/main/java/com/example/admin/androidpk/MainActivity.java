package com.example.admin.androidpk;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static Queue<String> mail = new LinkedList<>();
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMainActivity");
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.edit_text_IP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ResumeMainActivity");
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static boolean checkValidate(String s) {
        return true;
    }

    public void ButtonIPClick(View view) {
        Log.d(TAG, "ButtonIPClick");
        String IP = "192.168.211.246"; // WTF???
        //String IP = String.valueOf(mEditText.getText());
        if (checkValidate(IP)) {
            Intent intent = new Intent(this, DownloadActivity.class);
            startActivity(intent);
            intent = new Intent(this, ConnectService.class);
            intent.putExtra("IP", IP);
            startService(intent);
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("information", "Wrong IP-ddres");
            startActivity(intent);
        }
    }

    public static void send(String s) {
        synchronized (MainActivity.mail) {
            MainActivity.mail.add(s);
            MainActivity.mail.notify();
        }
    }

}
