package com.example.admin.androidpk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerokol.views.JoystickView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 16.12.2015.
 */
public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static final String LAYOUT_KEY = "cur_layout";
    private static final String CHOICE_KEY = "number_choice";
    private static final int MAGIC_NUMBER_BUTTON = 100; //it is unlikely to be 100 choices, so id Buttons from MenuActivity will be different
    private static final int MAGIC_NUMBER_ACCELEROMETR = 200;
    private static final int MAGIC_NUMBER_JOYSTICK = 300;
    public static final String APP_PREFERENCES_SETTINGS_BUTTONS = "settings_buttons";
    public static final String APP_REFERENCE_SETTINGS_JOYSTICKS = "settings_joystick";
    public static final String APP_REFERENCE_SETTINGS_ACCELEROMETR = "settings_accelerometr";
    private int countButton;
    private int countJoystick;
    private int haveAccelerometr;
    private SharedPreferences mSettings;
    private String appReference;
    private LinearLayout mSettingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_settings);
        mSettingsLayout = (LinearLayout) findViewById(R.id.set_settings);

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

    private LinearLayout createItem(String textView, int EditId, String commands) {
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
                editor.putString(APP_PREFERENCES_SETTINGS_BUTTONS + i, mCurEditText.getText().toString());
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
            for (int j = 0; j< 8; j++) {
                EditText mCurEditText = (EditText) findViewById(MAGIC_NUMBER_JOYSTICK + i*8 + j);
                if (mCurEditText.getText().toString() != "") {
                    editor.putString(APP_REFERENCE_SETTINGS_JOYSTICKS + i + "_" + j, mCurEditText.getText().toString());
                }
            }
        }
        editor.apply();
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

        ViewGroup viewGroup = mSettingsLayout;
        LinearLayout curLayout = (LinearLayout) View.inflate(this, curIdLayout, mSettingsLayout);
        int count = curLayout.getChildCount();
        countButton = 0;
        countJoystick = 0;
        haveAccelerometr = 0;
        ArrayList<View> linearLayoutViews = findAllChild(curLayout);
        for (View view: linearLayoutViews) {
            if (view.getClass() == Button.class) {
                countButton++;
            } else if (view.getClass() == JoystickView.class) {
                countJoystick++;
            } else if (view.getClass() == MyAccelerometer.class) {
                haveAccelerometr++;
                if (haveAccelerometr > 1) {
                    throw new IllegalStateException("Some accelerometres in one layout");
                }
            }
        }

        mSettingsLayout.removeAllViews();
        setContentView(mSettingsLayout);

        for (int i = 0; i < countButton; i++) {
            String commands = null;
            if (mSettings.contains(APP_PREFERENCES_SETTINGS_BUTTONS + i))
                commands = mSettings.getString(APP_PREFERENCES_SETTINGS_BUTTONS + i, commands);
            LinearLayout curButtonSettings = createItem("Button" + i, MAGIC_NUMBER_BUTTON + i, commands);
            mSettingsLayout.addView(curButtonSettings);
        }
        for (int i = 0; i < countJoystick; i++) {
            for (int j = 0; j < 8; j++) {
                String commands = null;
                if (mSettings.contains(APP_REFERENCE_SETTINGS_JOYSTICKS + i + "_" + j))
                    commands = mSettings.getString(APP_REFERENCE_SETTINGS_JOYSTICKS + i + "_" + j, commands);
                LinearLayout curJoystickSettings = createItem("Joystick" + i + "; Direction" + j, MAGIC_NUMBER_JOYSTICK + i*8 + j,
                        commands);
                mSettingsLayout.addView(curJoystickSettings);
            }
        }

        if (haveAccelerometr == 1) {
            String commands = null;
            if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left"))
                commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Left", commands);
            LinearLayout left = createItem("Left rotation", MAGIC_NUMBER_ACCELEROMETR + 0, commands);
            if (mSettings.contains(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right"))
                commands = mSettings.getString(APP_REFERENCE_SETTINGS_ACCELEROMETR + "_Right", commands);
            LinearLayout right = createItem("Right rotation", MAGIC_NUMBER_ACCELEROMETR + 1, commands);
            mSettingsLayout.addView(left);
            mSettingsLayout.addView(right);
        }

        Button buttonOk = new Button(this);
        ViewGroup.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        final Context curContext = this;
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFromEdits();
                Intent intent = new Intent(curContext, PlayActivity.class);
                intent.putExtra(LAYOUT_KEY, curIdLayout);
                intent.putExtra(CHOICE_KEY, curChoice);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        buttonOk.setLayoutParams(params);
        buttonOk.setText("Ok");
        mSettingsLayout.addView(buttonOk);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSettingsLayout.removeAllViews();
    }
}
