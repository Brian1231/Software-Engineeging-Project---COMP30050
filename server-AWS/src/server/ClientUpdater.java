package server;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

public class ClientUpdater{

	private String clientIP;

	public void setIP(String ip){
		this.clientIP = ip;
	}

	public void updateDesktop(){
		

			JSONObject output = null;

			try {
				output = new JSONObject("{}");
				output = Main.gameState.getInfo();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			if(clientIP!=null){
					try{
						System.out.println("Updating Desktop...");
						Socket socket = new Socket(Inet4Address.getByName(this.clientIP), 8080);
						System.out.println("Desktop connected");
						OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
						PrintWriter writer = new PrintWriter(out, true);
						writer.print(output.toString());
						socket.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				
			
		}
	}
}
