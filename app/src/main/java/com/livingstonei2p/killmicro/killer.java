package com.livingstonei2p.killmicro;

import android.content.Context;
import android.util.Log;

import java.io.File;

import static com.livingstonei2p.killmicro.shell.sudo;

public class killer {
    private File cacheDir, microCache, cameraCache, bootCache, gpsCache;
    private final String camera="/dev/*camera*";
    private final String camera0="/dev/video*";
    private final String microphone="/dev/snd/pcm*c";
    private final String GPS= "/dev/gps";
    private final String
        microCacheString = "/microCache",
            cameraCacheString = "/cameraCache",
            bootCacheString = "/sbootCache",
            gpsCacheString = "/gpsCache",
    ch000="chmod 000 ",
	    ch644="chmod 644 ";

    public void fixGPS() {
        sudo(ch644+GPS);

    }

    enum CacheSelect{
      micro,camera, gps, boot // 2,4,8?
    };
    public void KillCamera(){
        sudo(ch000+camera);
        sudo(ch000+camera0);
        //sudo("rm "+camera0);
    }
    public void killGPS(){
        sudo(ch000+GPS);
    }
    public void KillMicro(){
        sudo(ch000+microphone);
        //sudo("rm "+microphone);

    }
    public void FixCamera(){
        sudo(ch644+camera);
	sudo(ch644+camera0);
        //sudo("rm "+camera);
        //sudo("rm "+camera0);
    }
    public void FixMicro(){
        sudo(ch644+microphone);
        //sudo("rm "+microphone);

    }

    public boolean existCache(CacheSelect s){
        switch(s){
            case gps:
                return gpsCache.exists();
            case micro:
                return microCache.exists();
            case camera:
                return cameraCache.exists();
            case boot:
               return bootCache.exists();
        }
        return false;
    }
    public killer(Context appContext){
        cacheDir = appContext.getCacheDir();
        cacheDir.mkdirs();
        microCache = new File( cacheDir.getAbsolutePath() + microCacheString);
        cameraCache = new File( cacheDir.getAbsolutePath() + cameraCacheString);
        bootCache = new File( cacheDir.getAbsolutePath() + bootCacheString);
        gpsCache = new File(cacheDir.getAbsolutePath()+gpsCacheString);
    }

    public void createCache(CacheSelect s){
        try {
            Log.d("KillSrvc","create cache.");
            switch (s) {
                case micro:
                    Log.d("KillSrvc","create cache micro.");
                    microCache.createNewFile();
                    break;
                case camera:
                    Log.d("KillSrvc","create cache camera.");
                    cameraCache.createNewFile();
                    break;
                case boot:
                    Log.d("KillSrvc","create cache boot.");
                    bootCache.createNewFile();
                    break;
                case gps:
                    gpsCache.createNewFile();
                    break;
            }
        }catch(Exception e){
            Log.d("killSrvc",e.toString());
        }
    }
    public boolean checkSu(){
        if( sudo("echo test") == null) return false;
        return true;
    }
    public void deleteCache(CacheSelect s){
        Log.d("KillSrvc","delete cache.");
        switch(s){
            case micro:
                microCache.delete();
                break;
            case camera:
                cameraCache.delete();
                break;
            case boot:
                bootCache.delete();
                break;
            case gps:
                gpsCache.delete();
                break;
        }
    }

}
