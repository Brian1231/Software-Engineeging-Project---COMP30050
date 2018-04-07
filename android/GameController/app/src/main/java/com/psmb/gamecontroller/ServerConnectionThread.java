package com.psmb.gamecontroller;


import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ServerConnectionThread extends Thread {

    public final static String IP = "52.48.249.220";

    private ArrayList<String> messages;
    private int port;
    private Socket socket;

    private boolean mRun = true;
    MainActivity mainActivity;

    public ServerConnectionThread(MainActivity main, int p) {
        this.port = p;
        this.messages = new ArrayList<String>();
        this.mainActivity = main;
    }

    public void setMessage(String s){
        synchronized(messages){
            messages.add(s);
            messages.notify();
        }
    }
   /* private void send(String m) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //Send message
        out.println(m);
    }*/

    public void kill(){
        this.mRun = false;
    }
    @Override
    public void run() {

        try {
            socket = new Socket(IP, this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        BufferedWriter out = null;
        try {

            //out = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (mRun) {
            synchronized (messages) {
                if(!messages.isEmpty()){
                    try {
                        String m = messages.get(0);
                        messages.remove(0);
                        out.write(m+"\n");
                        out.flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                try {
                    //Input from server
                    if (reader.ready()) {
                        String line = reader.readLine();
                        if (!line.isEmpty()) {
                            mainActivity.handleResponse(line);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}