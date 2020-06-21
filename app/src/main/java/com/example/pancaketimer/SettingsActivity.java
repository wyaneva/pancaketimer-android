package com.example.pancaketimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pancaketimer.util.PrefUtil;

import org.w3c.dom.Text;

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
        final EditText editText_side1 = findViewById(R.id.editText_Side1);
        editText_side1.setText(Integer.toString(time1));
        int time2 = PrefUtil.getSide2Seconds(this);
        final EditText editText_side2 = findViewById(R.id.editText_Side2);
        editText_side2.setText(Integer.toString(time2));

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
                    final EditText editText_side1 = findViewById(R.id.editText_Side1);
                    final EditText editText_side2 = findViewById(R.id.editText_Side2);
                    String side1_str = editText_side1.getText().toString();
                    String side2_str = editText_side2.getText().toString();
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
