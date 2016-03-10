package ru.spbau.mit.androidcontroller.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();
    private FrameLayout mPreviewFrameLayout;
    private ListView mMenuChoiceListView;
    private int curSelection;
    private ArrayAdapter<String> listViewAdapter = null;
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayList<Integer> variatyLayoutsID = new ArrayList<>();

    private int setLayoutsChoice() { //compute count of choices
        int count = 0;
        String firstPart = "choice";
        while (++count > 0) {
            String curLayout = firstPart + count;
            final int curLayoutID = getResources().getIdentifier(curLayout, "layout", getApplicationContext().getPackageName());
            if (curLayoutID == 0) {
                break;
            } else if (findViewById(count) == null){
                variatyLayoutsID.add(curLayoutID);
                items.add("choice " + count);
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
        mMenuChoiceListView = (ListView) findViewById(R.id.menu_choice_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();    // hide action bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MenuActivity.onResume()");
            throw e;
        }

        curSelection = 0;
        items.clear();

        setLayoutsChoice();
        listViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        mMenuChoiceListView.setAdapter(listViewAdapter);
        mMenuChoiceListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curSelection = position;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void menuButtonOk(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("cur_layout_id", variatyLayoutsID.get(curSelection));
        intent.putExtra("number_choice", curSelection + 1);
        startActivity(intent);
    }


}
