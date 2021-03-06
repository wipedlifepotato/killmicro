package com.livingstonei2p.killmicro;

import android.content.Context;
import android.util.Log;

import java.io.File;

import static com.livingstonei2p.killmicro.shell.sudo;

public class killer {
    private File cacheDir, microCache, cameraCache, bootCache;
    private final String camera="/dev/*camera*";
    private final String camera0="/dev/video*";
    private final String microphone="/dev/snd/pcm*c";
    private final String
        microCacheString = "/microCache",
            cameraCacheString = "/cameraCache",
            bootCacheString = "/sbootCache";
    enum CacheSelect{
      micro,camera,boot // 2,4,8?
    };
    public void KillCamera(){
        sudo("chmod 000 "+camera);
        sudo("rm "+camera);
        sudo("rm "+camera0);
    }
    public void KillMicro(){
        sudo("chmod 000 "+microphone);
        sudo("rm "+microphone);

    }

    public boolean existCache(CacheSelect s){
        switch(s){
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
            }
        }catch(Exception e){
            Log.d("killSrvc",e.toString());
        }
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
        }
    }

}
