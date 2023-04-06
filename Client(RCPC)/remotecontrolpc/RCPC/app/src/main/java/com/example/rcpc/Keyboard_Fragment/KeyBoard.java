package com.example.rcpc.Keyboard_Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rcpc.Connection.Connection;
import com.example.rcpc.Constants_Variables.Constants;
import com.example.rcpc.Home_Fragment.Home;
import com.example.rcpc.Mouse_Fragment.MouseActivity;
import com.example.rcpc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class KeyBoard extends AppCompatActivity {

    EditText editText;
    String previousText = "";
    Button enter;
    Button clearAll;
    Button backSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        editText = findViewById(R.id.editText);
        enter = findViewById(R.id.enter);
        clearAll = findViewById(R.id.clearAll);
        backSpace = findViewById(R.id.backSpace);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                char ch = newCharacter(charSequence, previousText);
                if (ch == 0) {
                    return;
                }

                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Connection.out.println();
                                Connection.out.println(Character.toString(ch));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }

                previousText = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                                editText.setText("");
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

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                editText.setText("");
                                Connection.out.println();
                                Connection.out.println(Constants.Clear_All);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        });

        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.isConnected && Connection.out!=null) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Editable editable = editText.getText();
                                int length = editable.length();
                                if (length > 0) {
                                    editable.delete(length-1, length);
                                    Connection.out.println(Constants.Back_Space);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }

        });

/////////////////////////////////////////////////////////////////////////////////////////

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.keyboard);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(KeyBoard.this, Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mouse:
                        startActivity(new Intent(KeyBoard.this, MouseActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.keyboard:

                        return true;

                }

                return false;
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////
    }
    private char newCharacter(CharSequence currentText, CharSequence previousText) {
        char ch = 0;
        int currentTextLength = currentText.length();
        int previousTextLength = previousText.length();
        int difference = currentTextLength - previousTextLength;
        if (currentTextLength > previousTextLength) {
            if (1 == difference) {
                ch = currentText.charAt(previousTextLength);
            }
        } else if (currentTextLength < previousTextLength) {
            if (-1 == difference) {
                ch = '\b';
            } else {
                ch = ' ';
            }
        }
        return ch;
    }
}