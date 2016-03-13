package ru.spbau.mit.androidcontroller.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.spbau.mit.androidcontroller.tools.Protocol;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static final String LAYOUT_KEY = "cur_layout_id";
    private static final String CHOICE_KEY = "number_choice";
    public static final String APP_PREFERENCES_SETTINGS_BUTTONS = "settings_buttons";
    public static final String APP_REFERENCE_SETTINGS_JOYSTICKS = "settings_joystick";
    public static final String APP_REFERENCE_SETTINGS_ACCELEROMETR = "settings_accelerometr";
    public static Map<Integer, ArrayList<String>> viewEditTextMap = new HashMap<>();
    private int countButton;
    private int countJoystick;
    private int haveAccelerometer;
    private ArrayList<ListItem> items = new ArrayList<>();
    private EditViewAdapter adapter = null;
    private SharedPreferences mSettings;
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


    private ListItem createItem(String textView, String commands, View view) {
        return new ListItem(commands, textView, view);
    }

    private void saveFromEdits(EditViewAdapter adapter, int countButton, int countJoystick, int haveAccelerometr) {
        SharedPreferences.Editor editor = mSettings.edit();
        for (int i = 1; i <= countButton; i++) {
            editor.putString(APP_PREFERENCES_SETTINGS_BUTTONS + i, adapter.items.get(i - 1).editText);
        }

        for (int j = 1; j <= countJoystick; j++) {
            for (int dir = 0; dir < 8; dir++) {
                editor.putString(APP_REFERENCE_SETTINGS_JOYSTICKS + j + "_" + dir,
                        adapter.items.get(countButton + (j - 1)*8 + dir).editText);
            }
        }

        if (haveAccelerometr == 1) {
            editor.putString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left",
                    adapter.items.get(countButton + countJoystick*8).editText);
            editor.putString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right",
                    adapter.items.get(countButton + countJoystick * 8 + 1).editText);
        }
        editor.apply();
    }

    public static void send(int i) {
        MainActivity.send(i);
    }

    public static void setSettingsAndSendServer(Activity v) throws IllegalAccessException {
        send(Protocol.NEW_COMMAND);
        int countAllSets = 0;  //количество различных вариантов команд, по одному
        //для каждого направления джойстика, кнопки и 2 для акселерометра
        for (Integer viewId : viewEditTextMap.keySet()) {
            countAllSets += viewEditTextMap.get(viewId).size();
        }
        send(2 * countAllSets); //we distinguish commands on touching and
        // on 'untouching'. We send negative number if untouching.
        int curCount = 0;
        for (Integer viewId : viewEditTextMap.keySet()) {
            Integer[] commands = new Integer[2*viewEditTextMap.get(viewId).size()]; //count of
            // commands for one view
            for (int i = 0; i < 2*viewEditTextMap.get(viewId).size(); i++) {
                commands[i] = curCount++; //we number all possible set commands
            }
            for (String editText : viewEditTextMap.get(viewId)) {
                if (editText == null) {
                    editText = "";
                }
                String[] realCommands = editText.split(" ");
                if ((realCommands.length == 1) && (realCommands[0].equals(""))) {
                    send(0);
                    continue;
                } else {
                    send(realCommands.length);
                }
                ArrayList<Integer> values = new ArrayList<>();
                for (String realCommand : realCommands) {
                    if (HelpfulMethods.isNumeric(realCommand)) {
                        values.add(Integer.parseInt(realCommand));
                        send(Integer.parseInt(realCommand));
                    } else {
                        Integer value = null;
                        Field[] fields = KeyEvents.class.getDeclaredFields();
                        for (Field field: fields) {
                            String event = field.getName();
                            if (("VK_" + realCommand.toUpperCase()).equals(event)) {
                                value = field.getInt(null);
                                break;
                            }
                        }
                        if (value == null)
                            throw new NumberFormatException();
                        values.add(value);
                        send(value);
                    }
                }
                send(realCommands.length);
                for (int j = 2*realCommands.length - 1; j >= realCommands.length; j--) {
                    send(values.get(j - realCommands.length)); //send commands
                    //on 'untouching'
                }
            }
            View view = v.findViewById(viewId);
            ((Settingable) view).setSettings(commands); //set associating numbering with direction or
            // button
        }
        send(Protocol.RUN_COMMAND);
    }

    void groupById(EditViewAdapter adapter) {
        for (ListItem li: adapter.items) {
            Integer idView = li.view.getId();
            if (!viewEditTextMap.containsKey(idView)) { viewEditTextMap.put(idView, new ArrayList<String>()); }
            viewEditTextMap.get(idView).add(li.editText);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewEditTextMap.clear();            //hide action bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException in MainActivity.onResume()");
            throw e;
        }

        final int curChoice;                //extract curChoice, that take from previous activity
        final int curIdLayout;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curChoice = extras.getInt(CHOICE_KEY);
            curIdLayout = extras.getInt(LAYOUT_KEY);
        } else {
            setContentView(R.layout.invalid);
            return;
        }

        String appReference = "choice" + curChoice + "_settings";
        mSettings = getSharedPreferences(appReference, Context.MODE_PRIVATE);  //take settings for chosen pattern

        // to inflate this layout to be able to find view by id
        ViewGroup choiceLayout = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(curIdLayout, null);
        countButton = 0;
        countJoystick = 0;
        haveAccelerometer = 0;
        ArrayList<View> linearLayoutViews = findAllChild(choiceLayout);

        ListView mSettingsListView = new ListView(this);
        items.clear();  // the items of listview

        //find all objects on the pattern and then for each create some items
        for (View view: linearLayoutViews) {
            if (view.getClass() == MyButton.class) {
                countButton++;
                String commands = null;
                if (mSettings.contains(APP_PREFERENCES_SETTINGS_BUTTONS + countButton))
                    commands = mSettings.getString(APP_PREFERENCES_SETTINGS_BUTTONS + countButton, null);
                ListItem curButtonSettings = createItem("Button_" + ((MyButton)view).getLabel(countButton), commands, view);
                items.add(curButtonSettings);
            } else if (view.getClass() == MyJoystick.class) {
                countJoystick++;
                for (int j = 0; j < 8; j++) {
                    String commands = null;
                    if (mSettings.contains(APP_REFERENCE_SETTINGS_JOYSTICKS + countJoystick + "_" + j))
                        commands = mSettings.getString(APP_REFERENCE_SETTINGS_JOYSTICKS + countJoystick + "_" + j, null);
                    ListItem curJoystickSettings = createItem("Joystick_" + ((MyJoystick)view).getLabel(countJoystick) +
                                                                    "; Direction" + j, commands, view);
                    items.add(curJoystickSettings);
                }
            } else if (view.getClass() == MyAccelerometer.class) {
                haveAccelerometer++;
                if (haveAccelerometer > 1) {
                    throw new IllegalStateException("Some accelerometers are in the same layout");
                }
                String commands = null;
                if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left"))
                    commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left", null);
                ListItem left = createItem("Left rotation", commands, view);
                if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right"))
                    commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right", null);
                ListItem right = createItem("Right rotation", commands, view);
                items.add(left);
                items.add(right);
            }
        }

        //create own adapter to save Edit text and Text view in same time
        adapter = new EditViewAdapter(this, items);
        mSettingsListView.setAdapter(adapter);
        mSettingsSet.addView(mSettingsListView);
        mSettingsListView.setFocusable(false);
        LinearLayout.LayoutParams paramsListView = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paramsListView.weight = 0.9f;
        mSettingsListView.setLayoutParams(paramsListView);

        Button buttonOk = new Button(this);
        final Context curContext = this;
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save information in shared preference
                saveFromEdits(adapter, countButton, countJoystick, haveAccelerometer);
                groupById(adapter); //and save to viewEditTextMap
                Intent intent = new Intent(curContext, PlayActivity.class);
                intent.putExtra(LAYOUT_KEY, curIdLayout);
                startActivity(intent);
                }
            }

            );
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            params.weight=0.1f;
            buttonOk.setLayoutParams(params);
            buttonOk.setText("Ok");
            mSettingsSet.addView(buttonOk);
        }

        @Override
    protected void onPause() {
        super.onPause();
        mSettingsSet.removeAllViews();
    }
}
