package com.example.pancaketimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import com.example.pancaketimer.util.PrefUtil;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    int time_side1;
    int time_flip;
    int time_side2;

    // used by the settings menu
    int LAUNCH_SETTINGS = 1;
    boolean areSettingsUpdated = false;
    boolean isTimerRunning = false;

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
        if(value) {
            button_start.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        else {
            button_start.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryPale));
        }
    }

    void restorePreferences() {
        time_side1 = PrefUtil.getSide1Milliseconds(this);
        time_side2 = PrefUtil.getSide2Milliseconds(this);
        time_flip = PrefUtil.getFlipMilliseconds(this);

        TextView textView_mode = findViewById(R.id.textView_mode);
        int mode = PrefUtil.getMode(this);
        switch(mode) {
            case PrefUtil.MODE_PANCAKE:
                textView_mode.setText("Mode: Pancake");
                break;
            case PrefUtil.MODE_CREPE:
                textView_mode.setText("Mode: Crepe");
                break;
            case PrefUtil.MODE_CUSTOM:
                textView_mode.setText("Mode: Custom");
                break;
        }

        areSettingsUpdated = false;
    }

    void startTimer(final int counterId, final MediaPlayer mp) {

        isTimerRunning = true;

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
                if(areSettingsUpdated) {
                    restorePreferences();
                }
                isTimerRunning = false;
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

        restorePreferences();

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
                startActivityForResult(settingsIntent, LAUNCH_SETTINGS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LAUNCH_SETTINGS) {
            if(resultCode == Activity.RESULT_OK) {
                if(!isTimerRunning) {
                    restorePreferences();
                }
                else {
                    areSettingsUpdated = true;
                }
            }
        }
    }
}
