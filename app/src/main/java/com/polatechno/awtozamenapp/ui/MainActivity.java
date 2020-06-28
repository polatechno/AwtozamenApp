package com.polatechno.awtozamenapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.polatechno.awtozamenapp.R;
import com.polatechno.awtozamenapp.android.ImePreferences;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnEnableKeyboard, btnSelectDefaultInput, btnKPolatovKeboardSettings, btnDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnableKeyboard = (Button) findViewById(R.id.btnEnableKeyboard);
        btnSelectDefaultInput = (Button) findViewById(R.id.btnSelectDefaultInput);
        btnKPolatovKeboardSettings = (Button) findViewById(R.id.btnKPolatovKeboardSettings);
        btnDictionary = (Button) findViewById(R.id.btnDictionary);

        btnEnableKeyboard.setOnClickListener(this);
        btnSelectDefaultInput.setOnClickListener(this);
        btnKPolatovKeboardSettings.setOnClickListener(this);
        btnDictionary.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEnableKeyboard:
                startActivityForResult(
                        new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
                break;
            case R.id.btnKPolatovKeboardSettings:
                lunchPreferenceActivity();
                break;
            case R.id.btnSelectDefaultInput:
                if (isInputEnabled()) {
                    ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showInputMethodPicker();
                } else {
                    Toast.makeText(this, "Please enable keyboard first.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDictionary:
                startActivity(new Intent(this, DictionaryActivity.class));
                break;

            default:
                break;
        }


    }



    public boolean isInputEnabled() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> mInputMethodProperties = imm.getEnabledInputMethodList();

        final int N = mInputMethodProperties.size();
        boolean isInputEnabled = false;

        for (int i = 0; i < N; i++) {

            InputMethodInfo imi = mInputMethodProperties.get(i);
            Log.d("INPUT ID", String.valueOf(imi.getId()));
            if (imi.getId().contains(getPackageName())) {
                isInputEnabled = true;
            }
        }

        if (isInputEnabled) {
            return true;
        } else {
            return false;
        }
    }

    public void lunchPreferenceActivity() {
        if (isInputEnabled()) {
            Intent intent = new Intent(this, ImePreferences.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enable keyboard first.", Toast.LENGTH_SHORT).show();
        }
    }
}