package com.aier.speech.recognizer.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.aier.speech.recognizer.model.NetState;
import org.greenrobot.eventbus.EventBus;

public class NetStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            //说明网络是连接的
            int type = networkInfo.getType();
            switch (type) {
                case ConnectivityManager.TYPE_MOBILE:  //移动网络
               //     EventBus.getDefault().post(NetState.getInstance(true,"移动网络"));
                    break;
                case ConnectivityManager.TYPE_WIFI:  //wifi
                //    EventBus.getDefault().post(NetState.getInstance(true,"wifi已连接"));
                    break;
            }
        } else {
            EventBus.getDefault().post(NetState.getInstance(false,"网络不可用"));
        }
    }
}
