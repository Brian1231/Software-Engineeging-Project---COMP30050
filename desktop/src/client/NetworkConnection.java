package client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;


public class NetworkConnection {

    private String ip;
    private int port;

    private ConnectionThread networkThread = new ConnectionThread();

    public  NetworkConnection(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void startConnection() throws Exception {
        networkThread.start();
    }

    public void send(JSONObject output) throws Exception {
        System.out.println("sending message" + output.toString());
        PrintWriter writer = new PrintWriter(networkThread.out, true);
        writer.println(output.toString());
    }

    public void closeconnection() throws Exception {
        networkThread.socket.close();
    }

    private class ConnectionThread extends Thread {

        private Socket socket;
        private OutputStreamWriter out;
        private InputStreamReader in;

        public void run() {
            try {
                System.out.println("Connecting!");
                socket = new Socket(Inet4Address.getByName(ip), port);
                out = new OutputStreamWriter(socket.getOutputStream());
                in = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(in);

                JSONObject output = new JSONObject();
                output.put("id", 0);
                output.put("action", "start");
                send(output);

                while(true){
                    String message = reader.readLine();
                    if(!message.isEmpty()){
                        onMessage(message);
                    }
                }

            }catch(Exception e){e.printStackTrace();}
        }

        public void onMessage(String message) throws JSONException {
            System.out.println("message!" + message);
            JSONObject input = new JSONObject(message);
        }
    }
}
