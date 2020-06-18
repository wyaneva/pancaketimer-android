package com.example.pancaketimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pancaketimer.util.PrefUtil;

public class SettingsActivity extends AppCompatActivity {

    void finishActivityOK() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        getSupportActionBar().setTitle("Settings");

        final RadioButton radioButtonPancake = findViewById(R.id.radioButton_pancakemode);
        final RadioButton radioButtonCrepe = findViewById(R.id.radioButton_crepemode);
        final RadioButton radioButtonCustom = findViewById(R.id.radioButton_custommode);

        // TODO: initialise settings
        int mode = PrefUtil.getMode(this);
        switch (mode){
            case PrefUtil.MODE_PANCAKE:
                radioButtonPancake.setChecked(true);
                break;
            case PrefUtil.MODE_CREPE:
                radioButtonCrepe.setChecked(true);
                break;
            case PrefUtil.MODE_CUSTOM:
                radioButtonCustom.setChecked(true);
                break;
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(radioButtonPancake.isChecked()) {
                    PrefUtil.setMode(PrefUtil.MODE_PANCAKE, SettingsActivity.this);
                    finishActivityOK();
                    return;
                }

                if(radioButtonCrepe.isChecked()) {
                    PrefUtil.setMode(PrefUtil.MODE_CREPE, SettingsActivity.this);
                    finishActivityOK();
                    return;
                }

                if(radioButtonCustom.isChecked()) {
                    PrefUtil.setMode(PrefUtil.MODE_CUSTOM, SettingsActivity.this);
                    //TODO: set timings
                    finishActivityOK();
                    return;
                }
            }
        });
    }
}
