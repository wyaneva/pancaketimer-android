package com.example.pancaketimer.util;

import android.os.CountDownTimer;
import android.app.Activity;
import com.example.pancaketimer.MainActivity;

public class PancakeCountDownTimer extends CountDownTimer {

    long total_ms;
    int counterId;
    MainActivity parentActivity;

    CharSequence endText;
    Boolean isFlip;

    public PancakeCountDownTimer(MainActivity parent, int counterId, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.parentActivity = parent;
        this.counterId = counterId;
        this.total_ms = millisInFuture;
    }

    public void setParameters(CharSequence endText, Boolean isFlip) {
        this.endText = endText;
        this.isFlip = isFlip;
    }

    @Override
    public void onTick(long millisUntilFinished) {
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
        parentActivity.startTimer(counterId+1);
    }
}
