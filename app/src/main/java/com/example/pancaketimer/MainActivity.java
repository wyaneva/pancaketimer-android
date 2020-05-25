package com.example.pancaketimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    void startTimer(final long total_ms, final MediaPlayer mp) {
        final ProgressBar progress_countdown = findViewById(R.id.progress_countdown);
        final TextView textView_countdown= findViewById(R.id.textView_countdown);

        new CountDownTimer(total_ms, 1000) {

            public void onTick(long millisUntilFinished) {
                long sUntilFinished = millisUntilFinished / 1000 + 1; // dispay n to 1, not n-1 to 0
                long total_s = total_ms / 1000;
                long sElapsed = total_s - sUntilFinished;
                int progress = (int)((sElapsed*100)/total_s);

                textView_countdown.setText(sUntilFinished + "/" + total_s);
                progress_countdown.setProgress(100-progress);
            }

            public void onFinish() {
                textView_countdown.setText("Flip!");
                progress_countdown.setProgress(0);
                mp.start();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("Pancake Timer");
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);

        final Button button = findViewById(R.id.button_Start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTimer(10000, mp);
            }
        });
    }
}
