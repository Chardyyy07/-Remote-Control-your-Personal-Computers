package com.example.rcpc.Home_Fragment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rcpc.Connection.Connection;
import com.example.rcpc.Constants_Variables.Constants;
import com.example.rcpc.Keyboard_Fragment.KeyBoard;
import com.example.rcpc.Mouse_Fragment.MouseActivity;
import com.example.rcpc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Home extends AppCompatActivity {

    Connection connection;
    Context context;
    ImageButton connect;
    AlertDialog dialog;
    ImageButton shutDown, lock_screen, sleep, restart, fileEx, openSet, arrowUPKey, arrowLEFTKey, arrowDOWNKey, arrowRIGHTKey, calculator, webbrowser, notepad, help;
    Button enter, esc, tab, win, caps, delete, alt, ctrl, shift;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = Home.this;
        connection = new Connection(context);

        shutDown = findViewById(R.id.shutdown);
        lock_screen = findViewById(R.id.lock_screen);
        connect = findViewById(R.id.connect);
        sleep = findViewById(R.id.sleep);
        restart = findViewById(R.id.restart);
        fileEx = findViewById(R.id.fileEx);
        openSet = findViewById(R.id.openSet);
        arrowUPKey = findViewById(R.id.arrowUPKey);
        arrowLEFTKey = findViewById(R.id.arrowLEFTKey);
        arrowDOWNKey = findViewById(R.id.arrowDOWNKey);
        arrowRIGHTKey = findViewById(R.id.arrowRIGHTKey);
        enter = findViewById(R.id.enter);
        esc = findViewById(R.id.esc);
        tab = findViewById(R.id.tab);
        win = findViewById(R.id.win);
        caps = findViewById(R.id.caps);
        delete = findViewById(R.id.delete);
        alt = findViewById(R.id.alt);
        ctrl = findViewById(R.id.ctrl);
        calculator = findViewById(R.id.calculator);
        shift = findViewById(R.id.shift);
        webbrowser = findViewById(R.id.webbrowser);
        notepad = findViewById(R.id.notepad);
        help = findViewById(R.id.help);


/////////////////////////////////////////////////////////////////////////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter IP Address & Port");

        // Inflate the custom_dialog View
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        EditText ipAdd = view.findViewById(R.id.ipAdd);
        EditText port = view.findViewById(R.id.port);
        Button Submit = view.findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String SERVER_IP = ipAdd.getText().toString();
                int SERVER_PORT = Integer.parseInt(port.getText().toString());
                ipAdd.setText("IP Address :" + SERVER_IP);
                port.setText("Port :" + SERVER_PORT);
                dialog.dismiss();
            }
        });

        // Cancel button
        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Set the view to dialog
        builder.setView(view);
        // Create dialog now
        dialog = builder.create();
        // Connect button
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = new Connection(context);
                String SERVER_IP = ipAdd.getText().toString();
                int SERVER_PORT = Integer.parseInt(port.getText().toString());
                connection.execute(SERVER_IP, String.valueOf(SERVER_PORT)); //try to connect to server in another thread
                dialog.dismiss();
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////

        // Help dialog
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);

        View view2 = getLayoutInflater().inflate(R.layout.manual_dialogbox, null);
        helpBuilder.setView(view2);

        final AlertDialog helpDialog = helpBuilder.create();
        Button Ok = view2.findViewById(R.id.Ok);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.show();
            }
        });


/////////////////////////////////////////////////////////////////////////////////////////

        webbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.WEB_BROWSER);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        ctrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.CTRL);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.SHIFT);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        notepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.NOTE_PAD);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.DELETE);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.CALCU);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });


        caps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.CAPS_LOCK);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.WIN);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });


        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.TAB);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });


        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Constants.ALT);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.ESC);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.ENTER);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });
        arrowRIGHTKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.arrow_RIGHTKey);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });
        arrowDOWNKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.arrow_DOWNKey);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        arrowLEFTKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.arrow_LEFTKey);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        arrowUPKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.arrow_UPKey);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        openSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.OP_SETT);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        fileEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.FILE_EX);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.RESTART_PC);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.SLEEP_PC);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        lock_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.LOCK_SCREEN);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        shutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                Connection.out.println();
                                Connection.out.println(Constants.POWER_OFF);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:

                        return true;

                    case R.id.mouse:
                        startActivity(new Intent(Home.this, MouseActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.keyboard:
                        startActivity(new Intent(Home.this, KeyBoard.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });

    }

}