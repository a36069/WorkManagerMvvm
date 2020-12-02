package com.abdipor.workmanager.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.abdipor.workmanager.R;
import com.abdipor.workmanager.view.classhelper.WorkerNotif;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //send Data
        Data data=new Data.Builder().putString("name","des workSample").build();
        // filter in time network CONNECTED
        Constraints constraints=new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        //for ever time
        PeriodicWorkRequest request=new PeriodicWorkRequest
                .Builder(WorkerNotif.class,16, TimeUnit.MINUTES)
                .setConstraints(constraints)
//                .setInitialDelay(20,TimeUnit.SECONDS)
                .setInputData(data)
                .build();

        //for one time
//        OneTimeWorkRequest request=new  OneTimeWorkRequest.Builder(WorkerNotif.class).setConstraints(constraints).build();
        workManager=WorkManager.getInstance(this);
       workManager.enqueue(request);
       workManager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
           @Override
           public void onChanged(WorkInfo workInfo) {
               workManager.cancelWorkById(workInfo.getId());
           }
       });
    }
}