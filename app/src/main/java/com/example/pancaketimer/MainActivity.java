package com.example.pancaketimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    void startTimer() {
        final ProgressBar progress_countdown = findViewById(R.id.progress_countdown);
        progress_countdown.setProgress(50);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("Pancake Timer");

        final Button button = findViewById(R.id.button_Start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTimer();
                final TextView textView_countdown= findViewById(R.id.textView_countdown);
                textView_countdown.setText("Hello!");
            }
        });
    }
}
