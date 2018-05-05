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

    Button rollButton;
    Button doneButton;
    Button buyButton;
    Button sellButton;
    Button boostButton;
    Button mortgageButton;
    Button redeemButton;
    Button connectButton;
    Button buildButton;
    Button demolishButton;
    ServerConnectionThread thread;
    ServerConnectionThread gamethread;
    String character;
    public static int playerId;
    int balance;
    int position;
    public static boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollButton = findViewById(R.id.rollButton);
        connectButton = findViewById(R.id.connectButton);
        doneButton = findViewById(R.id.doneButton);
        buyButton = findViewById(R.id. buyButton);
        sellButton = findViewById(R.id.sellButton);
        boostButton = findViewById(R.id.boostButton);
        mortgageButton = findViewById(R.id.mortgageButton);
        redeemButton = findViewById(R.id.redeemButton);
        buildButton = findViewById(R.id.buildButton);
        demolishButton = findViewById(R.id.demolishButton);

        connected = false;

        this.playerId = 0;
        this.balance = 0;
        this.position = 0;
        this.character = "";
    }

    protected void onStart() {
        super.onStart();
        thread = new ServerConnectionThread(this, 8080);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "roll");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "done");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "buy");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "sell");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        boostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "boost");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        mortgageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "mortgage");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "redeem");
                    obj.put("args", "0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });

        buildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "build");
                    obj.put("args", "0, 0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });
        demolishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", MainActivity.playerId);
                    obj.put("action", "demolish");
                    obj.put("args", "0, 0");
                    if (gamethread != null) {
                        gamethread.setMessage(obj.toString());
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!connected) {
                    thread.start();
                    thread.setMessage("{\"id\":-1,\"action\":\"connect\", \"args\":\"0\"}");
                    thread.setMessage("{\"id\":-1,\"action\":\"connect\", \"args\":\"0\"}");
                }
            }
        });
    }

    public void setResponseText(String s){
        TextView restext = findViewById(R.id.responseText);
        restext.setText(s);
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
                    connected = true;
                    try {
                        JSONObject object = new JSONObject(s);
                        System.out.println("JSON: " + object.toString());
                        thread.kill();
                        gamethread = new ServerConnectionThread(this, (int)object.get("port"));
                        try {
                            this.playerId = (int) object.get("id");
                            this.balance = (int) object.get("balance");
                            this.position = (int) object.get("position");
                            this.character = (String) object.get("character");
                        }catch(Exception e){}
                        gamethread.start();
                        gamethread.setMessage("{\"id\":"+this.playerId+",\"action\":\"connect\", \"args\":\"0\"}");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(gamethread.isAlive()){
                    try {
                        JSONObject object = new JSONObject(s);
                        System.out.println("JSON: " + object.toString());
                        this.playerId = (int)object.get("id");
                        this.balance = (int)object.get("balance");
                        this.position = (int)object.get("position");
                        this.character = (String)object.get("character");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
