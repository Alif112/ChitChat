package com.example.reve.chitchat;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by REVE on 1/14/2020.
 */

public class BackgroundTask extends AsyncTask<String,Void,Void> {
    Socket socket;
    PrintWriter pw;
    InputStreamReader isr;
    BufferedReader br;
    @Override
    protected Void doInBackground(String... params) {
        try {
            String message;
            message = params[0];
            socket=new Socket("192.168.19.125",5000);
            pw=new PrintWriter(socket.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();

            isr=new InputStreamReader(socket.getInputStream());
            br=new BufferedReader(isr);
            message=br.readLine();
            System.out.println("--------------------------------->>>>>>>>>");
            System.out.println(message);


        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
