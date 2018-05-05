package pw.jcollado.segamecontroller.connections;


import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionThread extends Thread {

    public static String IP = "192.168.0.147";

    private ArrayList<String> messages;
    private int port;
    private Socket socket;

    private boolean mRun = true;
    private AsyncResponse mCallback;

    public ServerConnectionThread(Context context, int p) {
        this.port = p;
        this.messages = new ArrayList<>();
        this.mCallback = (AsyncResponse) context;
    }

    public void setMessage(String s){
        synchronized(messages){
            messages.add(s);
            messages.notify();
        }
    }


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

        } catch (Exception e1) {
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
                            mCallback.handleResponse(line);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
