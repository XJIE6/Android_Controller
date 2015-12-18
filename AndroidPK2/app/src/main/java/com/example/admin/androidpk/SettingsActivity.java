package com.example.admin.androidpk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 16.12.2015.
 */
public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static final String LAYOUT_KEY = "cur_layout_id";
    private static final String CHOICE_KEY = "number_choice";
    private static final int MAGIC_NUMBER_BUTTON = 100; //it is unlikely to be 100 choices, so id Buttons from MenuActivity will be different
    private static final int MAGIC_NUMBER_ACCELEROMETR = 200;
    private static final int MAGIC_NUMBER_JOYSTICK = 300;
    public static final String APP_PREFERENCES_SETTINGS_BUTTONS = "settings_buttons";
    public static final String APP_REFERENCE_SETTINGS_JOYSTICKS = "settings_joystick";
    public static final String APP_REFERENCE_SETTINGS_ACCELEROMETR = "settings_accelerometr";
    public static Map<Integer, ArrayList<EditText>> viewEditTextMap = new HashMap<>();
    private int countButton;
    private int countJoystick;
    private int haveAccelerometr;
    private ArrayList<LinearLayout> items = new ArrayList<>();
    private MyAdapter adapter = null;
    private SharedPreferences mSettings;
    private String appReference;
    private ListView mSettingsListView;
    private LinearLayout mSettingsSet;

    public static MyAccelerometer curAccelerometr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_settings);
        mSettingsSet = (LinearLayout) findViewById(R.id.set_settings);

        Log.d(TAG, "CreatePlayActivity");

    }

    private ArrayList<View> findAllChild(ViewGroup curViewGroup) {
        ArrayList<View> answer = new ArrayList<>();
        int count = curViewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View curView = curViewGroup.getChildAt(i);
            if (curView instanceof ViewGroup) {
                answer.addAll(findAllChild((ViewGroup) curView));
            }
            answer.add(curView);
        }
        return answer;
    }


    private LinearLayout createItem(String textView, int EditId, String commands, View view) {
        LinearLayout curViewSettings = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        curViewSettings.setOrientation(LinearLayout.HORIZONTAL);
        TextView curTextView = new TextView(this);
        ViewGroup.LayoutParams linearParams2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        curTextView.setText(textView);
        EditText curEditText = new EditText(this);
        ViewGroup.LayoutParams linearParams3 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        curEditText.setId(EditId);
        curEditText.setHint("Commands(sequential)");
        if (viewEditTextMap.get(view.getId()) == null) {
            viewEditTextMap.put(view.getId(), new ArrayList<EditText>());
        }
        viewEditTextMap.get(view.getId()).add(curEditText);
        if (commands != null) {
            curEditText.setText(commands);
        }
        curViewSettings.addView(curTextView, linearParams2);
        curViewSettings.addView(curEditText, linearParams3);
        return curViewSettings;
    }

    private void saveFromEdits() {
        SharedPreferences.Editor editor = mSettings.edit();
        for (int i = 0; i < countButton; i++) {
            EditText mCurEditText = (EditText) findViewById(MAGIC_NUMBER_BUTTON + i);
            if (mCurEditText.getText().toString() != "") {
                editor.putString(APP_PREFERENCES_SETTINGS_BUTTONS + (i + 1), mCurEditText.getText().toString());
            }
        }

        if (haveAccelerometr == 1) {
            EditText mLeftEditText = (EditText) findViewById(MAGIC_NUMBER_ACCELEROMETR + 0);
            if (mLeftEditText.getText().toString() != "") {
                editor.putString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left", mLeftEditText.getText().toString());
            }
            EditText mRightEditText = (EditText) findViewById(MAGIC_NUMBER_ACCELEROMETR + 1);
            if (mRightEditText.getText().toString() != "") {
                editor.putString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right", mRightEditText.getText().toString());
            }
        }

        for (int i = 0; i < countJoystick; i++) {
            for (int j = 0; j < 8; j++) {
                EditText mCurEditText = (EditText) findViewById(MAGIC_NUMBER_JOYSTICK + i*8 + j);
                if (mCurEditText.getText().toString() != "") {
                    editor.putString(APP_REFERENCE_SETTINGS_JOYSTICKS + (i + 1) + "_" + j, mCurEditText.getText().toString());
                }
            }
        }
        editor.apply();
    }

    public static void send(int i) {
        MainActivity.send(i);
    }

    public static void setSettingsAndSendServer(Activity v) {
        Log.d(TAG, "*1");
        send(1);
        int countAllSets = 0;
        for (Integer viewId : viewEditTextMap.keySet()) {
            countAllSets += viewEditTextMap.get(viewId).size();
        }
        Log.d(TAG, "$" + (2*countAllSets));
        send(2 * countAllSets);
        int curCount = 0;
        for (Integer viewId : viewEditTextMap.keySet()) {
            Integer[] commands = new Integer[2*viewEditTextMap.get(viewId).size()];
            Log.d(TAG, "" + 2*viewEditTextMap.get(viewId).size());
            for (int i = 0; i < 2*viewEditTextMap.get(viewId).size(); i++) {
                commands[i] = curCount++;
            }
            for (EditText editText : viewEditTextMap.get(viewId)) {
                String[] realCommands = editText.getText().toString().split(" ");
                Log.d(TAG, "#" + realCommands.length);
                if ((realCommands.length == 1) && (realCommands[0] == "")) {
                    send(0);
                    send(0);
                    continue;
                } else {
                    send(realCommands.length);
                }
                for (int j = 0; j < realCommands.length; j++) {
                    Log.d(TAG, "^" + realCommands[j]);
                    send(Integer.parseInt(realCommands[j]));
                }
                Log.d(TAG, "!" + realCommands.length);
                send(realCommands.length);
                for (int j = 2*realCommands.length - 1; j >= realCommands.length; j--) {
                    Log.d(TAG, "" + (-Integer.parseInt(realCommands[j - realCommands.length])));
                    send(-Integer.parseInt(realCommands[j - realCommands.length]));
                }
            }
            View view = v.findViewById(viewId);
            ((Settingable) view).setSettings(commands);
        }
        send(2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewEditTextMap.clear();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MainActivity.onResume()");
            throw e;
        }

        final int curChoice;
        final int curIdLayout;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curChoice = extras.getInt(CHOICE_KEY);
            curIdLayout = extras.getInt(LAYOUT_KEY);
        } else {
            setContentView(R.layout.invalid);
            return;
        }

        appReference = "choice" + curChoice + "_settings";
        mSettings = getSharedPreferences(appReference, Context.MODE_PRIVATE);

        ViewGroup choiceLayout = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(curIdLayout, null);;
        countButton = 0;
        countJoystick = 0;
        haveAccelerometr = 0;
        ArrayList<View> linearLayoutViews = findAllChild(choiceLayout);

        mSettingsListView = new ListView(this);
        items.clear();

        Button buttonOk = new Button(this);
        final Context curContext = this;
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFromEdits();
                Intent intent = new Intent(curContext, PlayActivity.class);
                intent.putExtra(LAYOUT_KEY, curIdLayout);
//                intent.putExtra(CHOICE_KEY, choiceLayout);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        buttonOk.setLayoutParams(params);
        buttonOk.setText("Ok");
        mSettingsSet.addView(buttonOk);


        Button buttonOk = new Button(this);
        final Context curContext = this;
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFromEdits();
                Intent intent = new Intent(curContext, PlayActivity.class);
                intent.putExtra(LAYOUT_KEY, curIdLayout);
//                intent.putExtra(CHOICE_KEY, choiceLayout);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        buttonOk.setLayoutParams(params);
        buttonOk.setText("Ok");
        mSettingsLayout.addView(buttonOk);


        for (View view: linearLayoutViews) {
            if (view.getClass() == MyButton.class) {
                countButton++;
                String commands = null;
                if (mSettings.contains(APP_PREFERENCES_SETTINGS_BUTTONS + countButton))
                    commands = mSettings.getString(APP_PREFERENCES_SETTINGS_BUTTONS + countButton, commands);
                LinearLayout curButtonSettings = createItem("Button_" + ((MyButton)view).getLabel(countButton),
                        MAGIC_NUMBER_BUTTON + countButton - 1, commands, view);
                items.add(curButtonSettings);
            } else if (view.getClass() == MyJoystick.class) {
                countJoystick++;
                for (int j = 0; j < 8; j++) {
                    String commands = null;
                    if (mSettings.contains(APP_REFERENCE_SETTINGS_JOYSTICKS + countJoystick + "_" + j))
                        commands = mSettings.getString(APP_REFERENCE_SETTINGS_JOYSTICKS + countJoystick + "_" + j, commands);
                    LinearLayout curJoystickSettings = createItem("Joystick_" + ((MyJoystick)view).getLabel(countJoystick) +
                                                                    "; Direction" + j,
                            MAGIC_NUMBER_JOYSTICK + (countJoystick - 1) * 8 + j, commands, view);
                    items.add(curJoystickSettings);
                }
            } else if (view.getClass() == MyAccelerometer.class) {
                haveAccelerometr++;
                if (haveAccelerometr > 1) {
                    throw new IllegalStateException("Some accelerometres in one layout");
                }
                String commands = null;
                if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left"))
                    commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left", commands);
                LinearLayout left = createItem("Left rotation", MAGIC_NUMBER_ACCELEROMETR + 0, commands, view);
                if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right"))
                    commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right", commands);
                LinearLayout right = createItem("Right rotation", MAGIC_NUMBER_ACCELEROMETR + 1, commands, view);
                items.add(left);
                items.add(right);
            }
        }

        adapter = new MyAdapter(this, items);
        mSettingsListView.setAdapter(adapter);
        mSettingsSet.addView(mSettingsListView);
        mSettingsListView.setFocusable(false);
/*        mSettingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSettingsListView.setSelection(position);
                mSettingsListView.requestFocus();
            }
        });
*/
//        EditText ed = new EditText(this);
 //       arl.add(ed);
 //       ArrayAdapter<EditText> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arl);

 //       ListView lv = new ListView(this);
  //      lv.setAdapter(aa);

 //       mSettingsSet.addView(lv);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSettingsSet.removeAllViews();
    }
}
