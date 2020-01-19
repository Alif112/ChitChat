package com.example.reve.chitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public EditText et;

    Socket socket;
    InputStreamReader isr;
    BufferedReader br;
    PrintWriter pw;
    String message;
    OutputStreamWriter outputStreamWriter;

    /**
     *
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.editText);



    }

    void send_data(View v) {
        message = et.getText().toString() + "\r\n";
        Thread myThread=new MyNewThread();
        myThread.start();
    }

   class MyNewThread extends Thread {

        @Override
        public void run() {
            try {

                socket=new Socket("192.168.19.125",6000);
                socket.setTcpNoDelay(true);
                System.out.println("----------------> Socket established");

                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();

                byte [] data = new byte[message.length() + 2];
                data[0] = (byte)((message.length() >> 8) & 0xff);
                data[1] = (byte)((message.length() & 0xff));

//                System.out.println(data[0]+" ---------> "+data[1]);
                System.arraycopy(message.getBytes(), 0, data, 2, message.length());

//                for(int i=0;i<message.getBytes().length;i++){
//                    System.out.println(message.getBytes()[i]);
//                }

                os.write(data);


//                outputStreamWriter.close(); //socket will be closed here

                System.out.println("--data send--------------in my thread run");
//                isr=new InputStreamReader(socket.getInputStream());
//                br=new BufferedReader(isr);
//                message=br.readLine();
                byte firstByte = (byte) is.read();
                byte secondByte = (byte) is.read();
                System.out.println(firstByte+"---- " + secondByte);
                int length = firstByte << 8 | secondByte;
                System.out.println("Receiving data of length: " + length);
                is.read(data, 0, length);

                message = new String(data,0,length);
                System.out.println("--------------------------------->>>>>>>>>");
                System.out.println(message);
                is.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
