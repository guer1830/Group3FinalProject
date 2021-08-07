package com.code.group3finalproject;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import com.code.group3finalproject.workmanager.NotificationWorker;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class TestNotificationWorker {
    @Test
    public void testSimpleEchoWorker() throws Exception {
        // Define input data

        Data input = new Data.Builder()
                .put("Worker Test", true)
                .build();

        // Create request
        PeriodicWorkRequest periodicWorkRequest =  new PeriodicWorkRequest.Builder(NotificationWorker.class,
                16, TimeUnit.MINUTES).setInputData(input).
                addTag("NOTIFICATION_WORKER")
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest);

        WorkManager workManager = WorkManager.getInstance(getApplicationContext());

        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(periodicWorkRequest).getResult().get();
        // Get WorkInfo and outputData
        WorkInfo workInfo = workManager.getWorkInfoById(periodicWorkRequest.getId()).get();
        Data outputData = workInfo.getOutputData();

        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.ENQUEUED));
    }

}
