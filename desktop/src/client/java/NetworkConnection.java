package client.java;

import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;


public class NetworkConnection {

	private boolean gameActive = false;
	private String ip;
	private int port;
	private ConnectionThread networkThread = new ConnectionThread();
	private Consumer<JSONObject> onReceiveCallBack;


	public  NetworkConnection(String ip, int port, Consumer<JSONObject> function){
		this.ip = ip;
		this.port = port;
		this.onReceiveCallBack = function;
	}

	public void startConnection() throws Exception {
		gameActive = true;
		networkThread.start();
	}

	public void send(JSONObject output) throws Exception {
		System.out.println("Sending :" + output.toString());
		PrintWriter writer = new PrintWriter(networkThread.out, true);
		writer.println(output.toString());
	}

	public void closeConnection() throws Exception {
		networkThread.socket.close();
		Platform.exit();
	}

	public void setConsumerfunction(Consumer<JSONObject> function) {
		this.onReceiveCallBack = function;
	}

	public void gameEnd(){
		gameActive = false;
	}


	// Thread which listens to the socket for game updates.
	private class ConnectionThread extends Thread {

		private Socket socket;
		private OutputStreamWriter out;
		private InputStreamReader in;

		public void run() {
			try {
				System.out.println("Connecting!");
				socket = new Socket(Inet4Address.getByName(ip), 8081);
				out = new OutputStreamWriter(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				JSONObject output = new JSONObject();
				output.put("id", 0);
				output.put("action", "start");
				send(output);
				//ServerSocket receiver = new ServerSocket(8081);
				//Socket recSocket;
				while(gameActive){
					//System.out.println("reading..");
					String message = "";
					if(reader.ready()){
						message = reader.readLine();
					}
					//System.out.println("here");
					if(!message.isEmpty()){
						//onMessage(message);
						System.out.println(message);
					}
				}


				closeConnection();

			}catch(Exception e){e.printStackTrace();}
		}

		public void onMessage(String message) throws JSONException {
			JSONObject input = new JSONObject(message);
			onReceiveCallBack.accept(input);
		}
	}
}
