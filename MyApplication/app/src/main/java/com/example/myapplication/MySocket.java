package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class MySocket implements Runnable {
    private String IP;
    private int port;
    private Socket socket = null;
    private PrintWriter out;
    private InputStream in;
    private boolean isRun = true;
    private PipedWriter pipedWriter = new PipedWriter();
    private char isConnected = 'N';

    public MySocket (String s, int p) {
        this.IP = s;
        this.port = p;
    }

    public PipedWriter getPipedWriter(){return pipedWriter;}
    public char getIsConnected() { return isConnected; }

    public void close(){
        isRun = false;
    }

    public void send(String msg){
        out.println(msg);
        out.flush();
    }

    @Override
    public void run() {
        try {
            socket = new Socket(IP,port);
            if(socket.isConnected()) isConnected = 'Y';

            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pipedWriter.write(isConnected);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isRun){}
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
