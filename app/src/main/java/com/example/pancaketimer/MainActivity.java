package com.example.pancaketimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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

import com.example.pancaketimer.util.PancakeCountDownTimer;
import com.example.pancaketimer.util.PrefUtil;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private MediaPlayer mediaPlayer;
    private PancakeCountDownTimer pancakeTimer;

    int time_side1;
    int time_flip;
    int time_side2;

    // used by the settings menu
    int LAUNCH_SETTINGS = 1;
    boolean areSettingsUpdated = false;
    boolean isTimerRunning = false;

    int pancakeCount = 1;
    boolean isTimerPaused = false;
    long timeLeftOnPausedTimer_ms = 0;
    int counterIdOnPausedTimer = 0;

    public void play_sound() {
        mediaPlayer.start();
    }

    public void write_progress(int progress) {
        final ProgressBar progress_countdown = findViewById(R.id.progress_countdown);
        progress_countdown.setProgress(progress);
    }

    public void write_progress(int progress, CharSequence text) {
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

    void setPauseAndStopButtonVisibility(Boolean value) {
        final Button button_Pause = findViewById(R.id.button_Pause);
        final Button button_Stop = findViewById(R.id.button_Stop);
        if(value){
            button_Pause.setVisibility(button_Stop.VISIBLE);
            button_Stop.setVisibility(button_Stop.VISIBLE);
        } else {
            button_Pause.setVisibility(button_Stop.GONE);
            button_Stop.setVisibility(button_Stop.GONE);
        }
    }

    void setStartButtonEnabled(Boolean value) {
        final Button button_start = findViewById(R.id.button_Start);
        if(value) {
            button_start.setVisibility(button_start.VISIBLE);
        }
        else {
            button_start.setVisibility(button_start.GONE);
        }
    }

    void resetPauseResumeButton(Boolean toPause) {
        final Button button_Pause = findViewById(R.id.button_Pause);
        if(toPause) {
            // To Pause
            button_Pause.setText("PAUSE");
            button_Pause.setTextColor(ContextCompat.getColor(this, R.color.colorTextPrimary));
        } else {
            // To Resume
            button_Pause.setText("RESUME");
            button_Pause.setTextColor(ContextCompat.getColor(this, R.color.colorTextAccent));
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

    public void startTimer(final int counterId, boolean isResume) {

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
                update_side("");
                total_ms = time_flip;
                isFlip = true;
                break;
            case 2: // side 2
                update_side("Side 2");
                total_ms = time_side2;
                endText = "Done!";
                break;
            default: // after all are done
                setStartButtonEnabled(true);
                setPauseAndStopButtonVisibility(false);
                pancakeCount++;
                if(areSettingsUpdated) {
                    restorePreferences();
                }
                isTimerRunning = false;
                return;
        }
        long remainingTime = isResume ? timeLeftOnPausedTimer_ms : total_ms;

        pancakeTimer = new PancakeCountDownTimer(this, counterId, remainingTime, 1000);
        pancakeTimer.setParameters(endText, isFlip, total_ms);
        pancakeTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("Pancake Timer");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);

        restorePreferences();

        // Set the start button
        final Button button_Start = findViewById(R.id.button_Start);
        button_Start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setStartButtonEnabled(false);
                setPauseAndStopButtonVisibility(true);
                startTimer(0, false);
            }
        });

        // Set the pause button
        final Button button_Pause = findViewById(R.id.button_Pause);
        button_Pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isTimerPaused) {
                    // Resume
                    startTimer(counterIdOnPausedTimer, true);
                    resetPauseResumeButton(true);
                } else {
                    // Pause
                    timeLeftOnPausedTimer_ms = pancakeTimer.getRemainingTime();
                    counterIdOnPausedTimer = pancakeTimer.getCounterId();
                    pancakeTimer.cancel();
                    resetPauseResumeButton(false);
                }
                isTimerPaused = !isTimerPaused;
            }
        });

        // Set the stop button
        final Button button_Stop = findViewById(R.id.button_Stop);
        button_Stop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                setStartButtonEnabled(true);
                setPauseAndStopButtonVisibility(false);
                pancakeTimer.cancel();
                update_side("");
                write_progress(0, "");
                isTimerRunning = false;
                isTimerPaused = false;
                resetPauseResumeButton(true);
            }
        });

        // Set visibility on the pause and stop buttons
        setPauseAndStopButtonVisibility(false);
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
