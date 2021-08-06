package com.code.group3finalproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.code.group3finalproject.databinding.ActivityNotificationSettingsBinding;
import com.code.group3finalproject.workmanager.NotificationWorker;

import java.util.concurrent.TimeUnit;

public class NotificationSettingsActivity extends AppCompatActivity {

    ActivityNotificationSettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.switchNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    initWorkManager(1);
                }else{
                    WorkManager.getInstance(NotificationSettingsActivity.this).cancelAllWork();
                }
            }
        });

        binding.btnSetInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(binding.etInterval.getText()) && Integer.parseInt(binding.etInterval.getText().toString())>14){
                    WorkManager.getInstance(NotificationSettingsActivity.this).cancelAllWork();
                    initWorkManager(Integer.parseInt(binding.etInterval.getText().toString()));
                }
            }
        });
    }

    private void initWorkManager(int interval){
        PeriodicWorkRequest periodicWorkRequest =  new PeriodicWorkRequest.Builder(NotificationWorker.class,
                interval, TimeUnit.MINUTES).
                addTag("NOTIFICATION_WORKER")
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }
}