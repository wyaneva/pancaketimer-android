package com.example.pancaketimer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        getSupportActionBar().setTitle("Settings");

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton radioButtonPancake = findViewById(R.id.radioButton_pancakemode);
                RadioButton radioButtonCrepe = findViewById(R.id.radioButton_crepemode);
                RadioButton radioButtonCustom = findViewById(R.id.radioButton_custommode);

                // TODO: continue
            }
        });
    }
}
