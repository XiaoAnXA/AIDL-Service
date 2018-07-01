package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    String TAG = "MyService";

    public Boolean MyFlags = false;

    public String data = "默认数据";

    public MyService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: Service Start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyFlags = true;
                while (MyFlags){
                    Log.e(TAG, "run: "+ data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyFlags = false;

        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {
                MyService.this.data = data;
            }
        };

    }
}
