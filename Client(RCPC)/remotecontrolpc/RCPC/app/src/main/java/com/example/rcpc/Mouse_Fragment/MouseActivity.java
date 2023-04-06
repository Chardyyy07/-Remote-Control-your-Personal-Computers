package com.example.rcpc.Mouse_Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rcpc.Connection.Connection;
import com.example.rcpc.Constants_Variables.Constants;
import com.example.rcpc.Home_Fragment.Home;
import com.example.rcpc.Keyboard_Fragment.KeyBoard;
import com.example.rcpc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MouseActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button leftClick;
    Button rightClick;
    TextView mousePad;

    public boolean isConnected=false;
    public boolean mouseMoved=false;
    public Socket socket;
    public PrintWriter out;

    public float initX =0;
    public float initY =0;
    public float disX =0;
    public float disY =0;

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this; //save the context to show Toast messages
        connection = new Connection(context);
        socket = connection.socket;
        out = connection.out;
        isConnected = connection.isConnected;

        //Get references of all buttons
        leftClick = (Button)findViewById(R.id.leftClick);
        rightClick = (Button)findViewById(R.id.rightClick);


        leftClick.setOnClickListener(MouseActivity.this);
        rightClick.setOnClickListener(this);

        //Get reference to the TextView acting as mousepad
        mousePad = (TextView)findViewById(R.id.mousePad);

        //capture finger taps and movement on the textview
        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected && out!=null){
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            //save X and Y positions when user touches the TextView
                            initX =event.getX();
                            initY =event.getY();
                            mouseMoved=false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            disX = event.getX()- initX; //Mouse movement in x direction
                            disY = event.getY()- initY; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                            initX = event.getX();
                            initY = event.getY();
                            if(disX !=0|| disY !=0){

                                Thread thread = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try  {
                                            //Code here
                                            out.println(disX +","+ disY); //send mouse movement to server
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();


                            }
                            mouseMoved=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            //consider a tap only if usr did not move mouse after ACTION_DOWN
                            if(!mouseMoved){

                                Thread thread = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try  {
                                            //Code here
                                            out.println();
                                            out.println(Constants.MOUSE_LEFT_CLICK);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();


                            }
                    }
                }
                else {
                    Log.d("debug", String.valueOf(new Connection().isConnected));
                }
                return true;
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.mouse);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(MouseActivity.this, Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mouse: //mainActivity

                        return true;
                    case R.id.keyboard:
                        startActivity(new Intent(MouseActivity.this, KeyBoard.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
    }

    //     OnClick method is called when any of the buttons are pressed
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftClick:
                if (isConnected && out!=null) {

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                //Code here

                                out.println();
                                out.println(Constants.MOUSE_LEFT_CLICK);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();


                }
                break;
            case R.id.rightClick:
                if (isConnected && out!=null) {

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                //Code here

                                out.println();
                                out.println(Constants.MOUSE_RIGHT_CLICK);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();


                }
                break;

        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(isConnected && out!=null) {
            try {
                out.println("exit"); //tell server to exit
                socket.close(); //close socket
            } catch (IOException e) {
                Log.e("RCPC", "Error in closing socket", e);
            }
        }
    }

}