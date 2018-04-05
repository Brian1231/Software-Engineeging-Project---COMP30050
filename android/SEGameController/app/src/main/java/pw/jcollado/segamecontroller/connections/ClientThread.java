package pw.jcollado.segamecontroller.connections;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends AsyncTask <String, Void, String> {

    private static Socket socket;

    private AsyncResponse mCallback;

    public ClientThread(Context context){
        this.mCallback = (AsyncResponse) context;
    }

    @Override
    protected String doInBackground(String... params) {

        String message = params[0];
        String SERVER_IP = params[1];
        int SERVER_PORT = Integer.parseInt(params[2]);
        String response = null;
        boolean connected;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVER_PORT);
            connected = true;
        }  catch (IOException e1) {
            e1.printStackTrace();
            connected = false;
        }

        if(connected) {
            //Send message
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                System.out.println(message);
                System.out.println("Sent");

                //Receive
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                response = reader.readLine();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //Close Socket
            finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            System.out.println("No Connection " + SERVER_IP + " , " + SERVER_PORT);
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        mCallback.processFinish(result);
    }
}