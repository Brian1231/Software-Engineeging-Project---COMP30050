package com.psmb.gamecontroller;

import android.os.Bundle;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    Button sendButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = (Button) findViewById(R.id.sendButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
    }

    protected void onStart() {
        super.onStart();
        //thread.delegate = this;
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.messageText);
                String message = et.getText().toString();
                EditText etip = (EditText) findViewById(R.id.ipText);
                String ip = etip.getText().toString();
                EditText etport = (EditText) findViewById(R.id.portText);
                String port = etport.getText().toString();


                new ClientThread(MainActivity.this).execute(message,ip, port);
            }
        });


    }

    @Override
    public void processFinish(String output){
        handleResponse(output);
    }

    public void handleResponse(String s){
        TextView restext = (TextView) findViewById(R.id.responseText);
        if(s == null) {
            restext.setText("null");
        }else{
            if (s.equals("")) {
                restext.setText("Blank response!");
            } else {
                restext.setText(s);
            }
        }
    }
}
