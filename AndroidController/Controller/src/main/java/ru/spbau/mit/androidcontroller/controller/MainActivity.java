package ru.spbau.mit.androidcontroller.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final Queue<Integer> mail = new LinkedList<>();  //the queue for messages to spend server
    private EditText mEditText;
    public static Boolean isStart = false;  //does the play activity show or not

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
        Log.d(TAG, "ResumeMainActivity");                    //hide action bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MainActivity.onResume()");
            throw e;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  (item.getItemId() == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    public void buttonIPClick(View view) {
        Log.d(TAG, "ButtonIPClick");
        String IP = String.valueOf(mEditText.getText());
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
        intent = new Intent(this, ConnectService.class);
        intent.putExtra("IP", IP);
        startService(intent);
    }

    public static void send(int s) {        // send the messages to the server
        synchronized (MainActivity.mail) {
            MainActivity.mail.add(s);
            MainActivity.mail.notify();
        }
    }

}
