package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

public class Main {

	public static HashMap<String, Integer> CLIENT_IP_PLAYER_ID_MAP;
	public static boolean serverActive = true;
	static final int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
		System.out.println(InetAddress. getLocalHost().getHostAddress());
		Server server = new Server(PORT);
		CLIENT_IP_PLAYER_ID_MAP = new HashMap<String, Integer>();
		
		while (serverActive){
			String response = server.listen();
			System.out.println(CLIENT_IP_PLAYER_ID_MAP);
			server.send(response);
			if(response.equals("Done")){
				serverActive = false;
			}
		}
	}
}
