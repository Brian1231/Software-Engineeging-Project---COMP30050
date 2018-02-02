package server;

import java.io.IOException;

public class Main {

	public static boolean serverActive = true;
	static final int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
		
		Server server = new Server(PORT);
		
		while (serverActive){
			String response = server.listen();
			server.send(response);
			if(response.equals("Done")){
				serverActive = false;
			}
		}
	}
}
