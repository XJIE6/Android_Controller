package com.example.admin.androidpk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Admin on 16.10.2015.
 */
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FrameLayout mPreviewFrameLayout;
    private LinearLayout mMenuChoiceLayout;
    private int curSelection;
    private ArrayList<Integer> variatyLayoutsID = new ArrayList<>();

    private int setLayoutsChoice() {
        int count = 0;
        String firstPart = "choice";
        while (++count > 0) {
            String curLayout = firstPart + count;
            final int curID = getResources().getIdentifier(curLayout, "layout", getApplicationContext().getPackageName());
            if (curID == 0) {
                break;
            } else if (findViewById(count) == null){
                variatyLayoutsID.add(curID);
                Button curButton = new Button(this);
                curButton.setText("choice " + count);
                curButton.setId(count);
                mMenuChoiceLayout.addView(curButton);
                final int curCount = count;
                curButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPreviewFrameLayout.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(curID, mPreviewFrameLayout);
                        curSelection = curCount - 1;
                    }
                });
                Log.d(TAG, curLayout);
            }
        }
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CreateMenuActivity");
        setContentView(R.layout.activity_menu);
        mPreviewFrameLayout = (FrameLayout) findViewById(R.id.preview_layout);
        mMenuChoiceLayout = (LinearLayout) findViewById(R.id.menu_choice_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MenuActivity.onResume()");
            throw e;
        }

        mPreviewFrameLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choice1, mPreviewFrameLayout);
        curSelection = 0;

        setLayoutsChoice();
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

    public void menuButtonOk(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("cur_layout", variatyLayoutsID.get(curSelection));
        startActivity(intent);
    }


}
