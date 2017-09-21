package com.example.stiangrim.hangman.Model;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.example.stiangrim.hangman.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stiantornholmgrimsgaard on 20.09.2017.
 */

public class ToastHandler {

    private Random randomGenerator = new Random();
    private Handler handler = new Handler();
    private List<String> encouragementToasts = new ArrayList<>();
    private Context context;

    public ToastHandler(Context context) {
        this.context = context;
        initiateEncouragementToasts();
    }

    private void initiateEncouragementToasts() {
        encouragementToasts.add(context.getString(R.string.toast_encouragement_0));
        encouragementToasts.add(context.getString(R.string.toast_encouragement_1));
        encouragementToasts.add(context.getString(R.string.toast_encouragement_2));
        encouragementToasts.add(context.getString(R.string.toast_encouragement_3));
        encouragementToasts.add(context.getString(R.string.toast_encouragement_4));
    }


    public void showEncouragementToast(int duration) {
        final Toast toast = getRandomEncouragementToast();
        toast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }

    private Toast getRandomEncouragementToast() {
        int index = randomGenerator.nextInt(encouragementToasts.size());
        Toast toast = Toast.makeText(context, encouragementToasts.get(index), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 800);
        return toast;
    }

    public void showDataClearedToast(int duration) {
        final Toast toast = Toast.makeText(context, context.getString(R.string.data_cleared), Toast.LENGTH_SHORT);
        toast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }
}
