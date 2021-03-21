package com.example.pancaketimer.util;

import android.os.CountDownTimer;
import android.app.Activity;
import com.example.pancaketimer.MainActivity;

public class PancakeCountDownTimer extends CountDownTimer {

    long total_ms;
    long remaining_ms;
    int counterId;
    MainActivity parentActivity;
    CharSequence endText;
    Boolean isFlip;


    public PancakeCountDownTimer(MainActivity parent, int counterId, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.remaining_ms = millisInFuture;
        this.counterId = counterId;
        this.parentActivity = parent;
    }

    public void setParameters(CharSequence endText, Boolean isFlip, long totalTime) {
        this.endText = endText;
        this.isFlip = isFlip;
        this.total_ms = totalTime;
    }

    public long getRemainingTime() {
        return remaining_ms;
    }

    public int getCounterId() {
        return counterId;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        remaining_ms = millisUntilFinished;

        // progress display
        long sUntilFinished = millisUntilFinished / 1000 + 1; // dispay n to 1, not n-1 to 0
        long total_s = total_ms / 1000;
        int progress = (int) ((sUntilFinished * 100) / total_s);
        CharSequence text = sUntilFinished + "/" + total_s;

        if (!isFlip) {
            parentActivity.write_progress(progress, text);
        } else {
            parentActivity.write_progress(progress);
        }
    }

    @Override
    public void onFinish() {
        parentActivity.write_progress(0, endText);
        if(!isFlip) {
            parentActivity.play_sound();
        }
        parentActivity.startTimer(counterId+1, false);
    }
}
