package com.example.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.aidlservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    String TAG = "another";

    public EditText editText;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.et_data);
        intent= new Intent();
        //设置组件（4大组件）
        intent.setComponent(new ComponentName("com.example.aidlservice","com.example.aidlservice.MyService"));
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(this);
        findViewById(R.id.btn_bund).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:

                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
            case R.id.btn_bund:
                bindService(intent,this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(this);
                binder = null;
                break;
            case R.id.btn_send:
                if (binder != null){
                    try {
                        binder.setData(editText.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.e(TAG, "onServiceConnected: " );
        binder = IMyAidlInterface.Stub.asInterface(iBinder);
        Log.d(TAG, "onServiceConnected: "+iBinder);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    private IMyAidlInterface binder  = null;
}
