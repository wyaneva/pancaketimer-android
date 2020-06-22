package com.example.pancaketimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pancaketimer.util.PrefUtil;

public class SettingsActivity extends AppCompatActivity {

    void finishActivityOK() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    void enableRadioButtons() {
        final RadioButton radioButtonPancake = findViewById(R.id.radioButton_pancakemode);
        final RadioButton radioButtonCrepe = findViewById(R.id.radioButton_crepemode);
        final RadioButton radioButtonCustom = findViewById(R.id.radioButton_custommode);
        final EditText editTextSide1 = findViewById(R.id.editText_Side1);
        final EditText editTextSide2 = findViewById(R.id.editText_Side2);

        if(radioButtonPancake.isChecked() || radioButtonCrepe.isChecked()) {
            editTextSide1.setEnabled(false);
            editTextSide2.setEnabled(false);
            return;
        }

        if(radioButtonCustom.isChecked()) {
            editTextSide1.setEnabled(true);
            editTextSide2.setEnabled(true);
            return;
        }
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

        // Initialise controls
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

        int time1 = PrefUtil.getSide1Seconds(this);
        final EditText editTextSide1 = findViewById(R.id.editText_Side1);
        editTextSide1.setText(Integer.toString(time1));
        int time2 = PrefUtil.getSide2Seconds(this);
        final EditText editTextSide2 = findViewById(R.id.editText_Side2);
        editTextSide2.setText(Integer.toString(time2));
        enableRadioButtons();

        // Set listener on radio buttons
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableRadioButtons();
            }
        };
        radioButtonPancake.setOnCheckedChangeListener(listener);
        radioButtonCrepe.setOnCheckedChangeListener(listener);
        radioButtonCustom.setOnCheckedChangeListener(listener);

        // Set listener on Save button
        final Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
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
                    String side1_str = editTextSide1.getText().toString();
                    String side2_str = editTextSide2.getText().toString();
                    if(side1_str.isEmpty() || side2_str.isEmpty()) {
                        CharSequence text = "Please, enter custom times \nfor Side 1 and Side 2.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(SettingsActivity.this, text, duration);
                        toast.show();
                    } else {
                        int side1 = Integer.parseInt(side1_str);
                        int side2 = Integer.parseInt(side2_str);
                        PrefUtil.setSide1Seconds(side1, SettingsActivity.this);
                        PrefUtil.setSide2Seconds(side2, SettingsActivity.this);
                        finishActivityOK();
                    }
                    return;
                }
            }
        });
    }
}
