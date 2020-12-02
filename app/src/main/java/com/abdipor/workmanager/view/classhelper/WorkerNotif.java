package com.abdipor.workmanager.view.classhelper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.viewbinding.BuildConfig;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.abdipor.workmanager.R;

public class WorkerNotif extends Worker {
    public WorkerNotif(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data=getInputData();
        showNotif("sss","dddd: "+data.getString("name"));

        return Result.success();
    }

    public void showNotif(String titl,String des){
        NotificationManager notificationManager=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String chaneldId="chcklogindid";
        if(Build.VERSION.SDK_INT >26){
            NotificationChannel notificationChannel=new NotificationChannel(chaneldId,"test",NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationCompat=new NotificationCompat.Builder(getApplicationContext(),chaneldId)
                .setContentTitle(titl).setContentText(des).setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1,notificationCompat.build());
    }
}
