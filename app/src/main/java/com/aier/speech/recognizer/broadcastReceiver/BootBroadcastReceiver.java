package com.aier.speech.recognizer.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aier.speech.recognizer.mvp.activity.FirstActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent in = new Intent(context, FirstActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }
    }
}
