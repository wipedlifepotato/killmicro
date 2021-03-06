package com.livingstonei2p.killmicro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.util.List;

import static com.livingstonei2p.killmicro.R.id.killmicroswitch;

public class SettingsActivity extends Activity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Switch killmicroswitch = findViewById(R.id.switch_micro);
        Switch killcameraswitch = findViewById(R.id.switch_camera);
        CheckBox startonboot = findViewById(R.id.startonbutton);
        killer k = new killer(getApplicationContext());
        killcameraswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    k.KillCamera();
                    k.createCache(killer.CacheSelect.camera);
                }else
                    k.deleteCache(killer.CacheSelect.camera);

            }
        });
        if(k.existCache(killer.CacheSelect.micro)){
            killmicroswitch.setChecked(true);
        }
        if(k.existCache(killer.CacheSelect.camera)){
            killcameraswitch.setChecked(true);
        }
        if(k.existCache(killer.CacheSelect.boot)){
            startonboot.setEnabled(true);
            startonboot.setChecked(true);
        }
        killmicroswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    k.KillMicro();
                    k.createCache(killer.CacheSelect.micro);
                }else
                    k.deleteCache(killer.CacheSelect.micro);
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

    }
}