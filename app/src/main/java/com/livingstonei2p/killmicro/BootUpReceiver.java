package com.livingstonei2p.killmicro;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /* todo: disable the autostart? */
        killer k = new killer(context.getApplicationContext());
        if(k.existCache(killer.CacheSelect.boot)) {
            Log.d("KillMicroService","Inited...");
            if(k.existCache(killer.CacheSelect.micro)){
                k.KillMicro();
            }
            if(k.existCache(killer.CacheSelect.camera)){
                k.KillCamera();
            }
            if(k.existCache(killer.CacheSelect.gps)){
                k.killGPS();
            }
        }

    }
}

