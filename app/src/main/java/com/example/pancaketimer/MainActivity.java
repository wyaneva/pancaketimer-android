package com.example.pancaketimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final int time_side1 = 85000;
    final int time_flip = 5000;
    final int time_side2 = 60000;

    //final int time_side1 = 5000;
    //final int time_flip = 2000;
    //final int time_side2 = 6000;

    int pancakeCount = 1;

    void write_progress(int progress) {
        final ProgressBar progress_countdown = findViewById(R.id.progress_countdown);
        progress_countdown.setProgress(progress);
    }

    void write_progress(int progress, CharSequence text) {
        write_progress(progress);
        final TextView textView_countdown= findViewById(R.id.textView_countdown);
        textView_countdown.setText(text);
    }

    void update_pancake() {
        final TextView textView_side = findViewById(R.id.textView_pancakecount);
        textView_side.setText("Pancake " + pancakeCount);
    }

    void update_side(CharSequence text) {
        final TextView textView_side = findViewById(R.id.textView_side);
        textView_side.setText(text);
    }

    void setButtonEnabled(Boolean value) {
        final Button button_start = findViewById(R.id.button_Start);
        button_start.setEnabled(value);
    }

    void startTimer(final int counterId, final MediaPlayer mp) {

        long total_ms = 0;
        CharSequence endText = "Flip!";
        Boolean isFlip = false;

        switch(counterId){
            case 0: // side 1
                update_side("Side 1");
                update_pancake();
                total_ms = time_side1;
                break;
            case 1: // flip time
                total_ms = time_flip;
                isFlip = true;
                break;
            case 2: // side 2
                update_side("Side 2");
                total_ms = time_side2;
                endText = "Done!";
                break;
            default:
                setButtonEnabled(true);
                pancakeCount++;
                return;
        }

        final long finalTotal_ms = total_ms;
        final CharSequence finalEndText = endText;
        final Boolean finalIsFlip = isFlip;

        new CountDownTimer(finalTotal_ms, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long sUntilFinished = millisUntilFinished / 1000 + 1; // dispay n to 1, not n-1 to 0
                long total_s = finalTotal_ms / 1000;
                int progress = (int) ((sUntilFinished * 100) / total_s);
                CharSequence text = sUntilFinished + "/" + total_s;

                if (!finalIsFlip) {
                    write_progress(progress, text);
                } else {
                    write_progress(progress);
                }
            }

            @Override
            public void onFinish() {
                write_progress(0, finalEndText);
                if(!finalIsFlip) {
                    mp.start();
                }
                startTimer(counterId+1, mp);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("Pancake Timer");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);

        final Button button = findViewById(R.id.button_Start);
        final TextView textView_side = findViewById(R.id.textView_side);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setButtonEnabled(false);
                startTimer(0, mp);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
