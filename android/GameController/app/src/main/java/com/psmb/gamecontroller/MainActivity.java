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
    Button cancelButton;
    ServerConnectionThread thread;
    ServerConnectionThread gamethread;
    String res = "Nothing yet.";

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
        thread = new ServerConnectionThread(this, 8080);
        thread.start();
        EditText et = (EditText) findViewById(R.id.messageText);
        thread.setMessage(et.getText().toString());
//        thread.send();
        String s = "";
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.messageText);
                if(gamethread!= null) {
                    gamethread.setMessage(et.getText().toString());
                }
            }
        });
    }

    public void handleResponse(String s){
        TextView restext = (TextView) findViewById(R.id.responseText);
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
                        gamethread.start();
//                        EditText et = (EditText) findViewById(R.id.messageText);
//                        gamethread.setMessage(et.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
