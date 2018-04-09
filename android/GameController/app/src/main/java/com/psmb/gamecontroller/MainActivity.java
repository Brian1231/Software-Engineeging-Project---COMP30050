package com.psmb.gamecontroller;

import android.os.Bundle;
import android.widget.Button;
import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity{

    Button sendButton;
    Button connectButton;
    ServerConnectionThread thread;
    ServerConnectionThread gamethread;
    int playerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = findViewById(R.id.sendButton);
        connectButton = findViewById(R.id.connectButton);
    }

    protected void onStart() {
        super.onStart();
        thread = new ServerConnectionThread(this, 8080);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText et = findViewById(R.id.messageText);
                if(gamethread!= null) {
                    gamethread.setMessage(et.getText().toString());
                }
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                thread.start();
                EditText et = (EditText) findViewById(R.id.messageText);
                thread.setMessage(et.getText().toString());
                thread.setMessage(et.getText().toString());
            }
        });
    }

    public void handleResponse(String s){
        TextView restext = findViewById(R.id.responseText);
        System.out.println(s);
        if(s == null) {
            restext.setText("null");
        }else{
            if (s.equals("")) {
                restext.setText("Blank response!");
            } else {
                restext.setText(s);
                if(thread.isAlive()) {
                    try {
                        JSONObject object = new JSONObject(s);
                        System.out.println("JSON: " + object.toString());
                        thread.kill();
                        gamethread = new ServerConnectionThread(this, (int)object.get("port"));
                        System.out.println((int)object.get("port"));
                        this.playerId = (int)object.get("id");
//                        EditText et = findViewById(R.id.messageText);
//                        et.setText("{\"id\":"+this.playerId+",\"action\":\"roll\", \"args\":\"0\"}");
                        gamethread.start();
                        gamethread.setMessage("{\"id\":"+this.playerId+",\"action\":\"connect\", \"args\":\"0\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
