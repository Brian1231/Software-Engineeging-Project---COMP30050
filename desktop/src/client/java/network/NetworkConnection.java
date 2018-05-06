package client.java.network;

import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.function.Consumer;

/*
	Handles all network activity.
	Connects to server.
	Receives messages from server.
	Sends messages to the server.

	Contains an inner class which provides a thread in order to perform all network tasks on a separate thread to the ui thread.
 */

public class NetworkConnection {

	private boolean gameActive = false;
	private String ip;
	private int port;
	private ConnectionThread networkThread = new ConnectionThread();
	private Consumer<JSONObject> onReceiveCallBack;

	public NetworkConnection(String ip, int port, Consumer<JSONObject> function){
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

	private void closeConnection() throws Exception {
		networkThread.socket.close();
		Platform.exit();
	}

	public void gameEnd(){
		gameActive = false;
	}

	// Thread which listens to the socket for game updates.
	private class ConnectionThread extends Thread {

		private Socket socket;
		private OutputStreamWriter out;
		//private InputStreamReader in;

		public void run() {
			try {
				System.out.println("Connecting!");
				socket = new Socket(Inet4Address.getByName(ip), port);
				out = new OutputStreamWriter(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while(gameActive){
					String message = "";
					if(reader.ready()) {
						message = reader.readLine();
					}
					if(!message.isEmpty()){
						onMessage(message);
					}
				}
				closeConnection();
			}catch(Exception e){
				try {
					gameActive=false;
					onMessage("{f : falseConnection}");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}

		// Calls back to method in game controller
		void onMessage(String message) throws JSONException {
			JSONObject input = new JSONObject(message);
			onReceiveCallBack.accept(input);
		}
	}
}
