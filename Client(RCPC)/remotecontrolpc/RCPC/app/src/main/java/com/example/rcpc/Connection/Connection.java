package com.example.rcpc.Connection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connection extends AsyncTask<String,Void,Boolean>{

    public static Socket socket;
    public static PrintWriter out;
    public static boolean isConnected=false;
    public Context context;

    public Connection(Context context){
        this.context = context;
    }
    public Connection(){

    }


    @Override
    public Boolean doInBackground(String... params) {
        boolean result = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(params[0]);
            int SERVER_PORT = Integer.parseInt(params[1]);
            socket = new Socket(serverAddr,SERVER_PORT);//Open socket on server IP and port
        } catch (IOException e) {
            Log.e("rcpc", "Error while connecting", e);
            result = false;
        }
        return result;
    }


    @Override
    public void onPostExecute(Boolean result)
    {
        isConnected = result;
        Toast.makeText(context,isConnected?"Connected to server!":"Error while connecting",Toast.LENGTH_LONG).show();
        try {
            if(isConnected) {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true); //create output stream to send data to server
            }
        }catch (IOException e){
            Log.e("rcpc", "Error while creating OutWriter", e);
            Toast.makeText(context,"Error while connecting",Toast.LENGTH_LONG).show();
        }

    }
}
