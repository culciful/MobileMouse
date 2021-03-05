package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.fragment.ConnectFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity2";
    private ConnectFragment connectFragment;
    private static MySocket mySocket = null;
    ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectFragment = new ConnectFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, connectFragment,"a").addToBackStack(null).commitAllowingStateLoss();

    }

    public  void startSocket(String IP,int port){
        mySocket = new MySocket(IP,port);
        exec.execute(mySocket);
        int count = ((ThreadPoolExecutor)exec).getActiveCount();
        Log.d("getActiveCount"," "+count);
    }

    public void sendMessage(final String message){
        exec.execute(new Runnable() {
            @Override
            public void run() {
                mySocket.send(message);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close();
    }

    public void close(){
        mySocket.close();
        exec.shutdown();
        finish();
    }
}