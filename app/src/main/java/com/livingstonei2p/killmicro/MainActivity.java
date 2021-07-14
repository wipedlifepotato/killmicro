package com.livingstonei2p.killmicro;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private killer k;
    private TextView killMicroStatus, killCameraStatus, OnBootStatus, killGpsStatus,rootStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button openSettings = findViewById(R.id.openSettings);
         k = new killer(getApplicationContext());
        // killMicroStatus, killCameraStatus, OnBootStatus;
        killMicroStatus = findViewById(R.id.mStatus);
        killCameraStatus = findViewById(R.id.mStatus2);
        OnBootStatus = findViewById(R.id.mStatus3);
        killGpsStatus = findViewById(R.id.mStatus4);
        rootStatus = findViewById(R.id.mStatus5);
        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrefs();
            }
        });
        //settingsText = (TextView) findViewById(R.id.settingsText);
    }
    private void addAutoStartupswitch() {
        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER .toLowerCase();

            switch (manufacturer){
                case "xiaomi":
                    intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                    break;
                case "oppo":
                    intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                    break;
                case "vivo":
                    intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                    break;
                case "Letv":
                    intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
                    break;
                case "Honor":
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                    break;
                case "oneplus":
                    intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"));
                    break;
            }

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if  (list.size() > 0) {
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exceptionGetComponent" , String.valueOf(e));
        }

    }
    /*
        Switch killmicroswitch = findViewById(R.id.killmicroswitch);
        Switch killcameraswitch = findViewById(R.id.killcameraswitch);
        CheckBox startonboot = findViewById(R.id.startonbootswitch);
       killcameraswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    k.KillCamera();
                    k.createCache(killer.CacheSelect.camera);
                }else{
		    k.FixCamera();
                    k.deleteCache(killer.CacheSelect.camera);
		}

            }
        });

        killmicroswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    k.KillMicro();
                    k.createCache(killer.CacheSelect.micro);
                }else{
                    k.deleteCache(killer.CacheSelect.micro);
		    k.FixMicro();
		}
            }
        });
        startonboot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    k.createCache(killer.CacheSelect.boot);
                    addAutoStartupswitch();
                } else
                    k.deleteCache(killer.CacheSelect.boot);
            }
        });

 */
    boolean killMicro, killCamera, startOnBoot, OpenedAutoStart, killGPS, killAudio;
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(k.existCache(killer.CacheSelect.micro)){
            killMicro=true;
        }
        if(k.existCache(killer.CacheSelect.camera)){
            killCamera=true;
        }
        if(k.existCache(killer.CacheSelect.boot)){
            startOnBoot=true;
        }
        if(k.existCache(killer.CacheSelect.gps)){
            killGPS=true;
        }
        killMicro = prefs.getBoolean("killmicro", killMicro);
        killCamera = prefs.getBoolean("killcamera", killCamera);
        startOnBoot = prefs.getBoolean("startonboot",startOnBoot);
        killGPS = prefs.getBoolean("killGPS", killGPS);
	killAudio = prefs.getBoolean("killaudio", killAudio);
        if (!k.checkSu()){
            rootStatus.setText("ROOT прав не найдено! Приложение не будет работать корректно!");

        }
	if( killAudio ){
		k.killAudio();
	}
        if( startOnBoot){
            OnBootStatus.setText("Включаться при загрузке: включено");
            if(!OpenedAutoStart) {
                addAutoStartupswitch();
                OpenedAutoStart=true;

            }
            Log.d("Preferences","OnBOOT ENABLED");
        }else{
            k.deleteCache(killer.CacheSelect.boot);
            OnBootStatus.setText("Включаться при загрузке: выключено");

        }
        if(killGPS){
            k.killGPS();
            k.createCache(killer.CacheSelect.gps);
            killGpsStatus.setText("GPS выключено (?)");
        }else{
            k.fixGPS();
            k.deleteCache(killer.CacheSelect.gps);
            killGpsStatus.setText("GPS включено (?)");
        }
        if(killCamera){
            k.KillCamera();
            k.createCache(killer.CacheSelect.camera);
            Log.d("Preferences","killcamera ENABLED");
            killCameraStatus.setText("Камера: выключена");

        }else {
            k.deleteCache(killer.CacheSelect.camera);
            Log.d("Preferences","killcamera DISABLED");
            killCameraStatus.setText("Камера: включена");

        }

        if(killMicro){
            k.KillMicro();
            k.createCache(killer.CacheSelect.micro);
            Log.d("Preferences","killmicro ENABLED");
            killMicroStatus.setText("Микрофон: выключен");
        }else {
            k.deleteCache(killer.CacheSelect.micro);
            Log.d("Preferences","killmicro DISABLED");
            killMicroStatus.setText("Микрофон: включен");

        }
       // settingsText.setText(login);
    }

    public void setPrefs(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}


