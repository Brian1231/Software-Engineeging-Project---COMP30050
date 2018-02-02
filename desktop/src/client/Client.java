package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

class Client {

	final static String IP = "52.211.177.158";
	final static int PORT = 8080;
	final static String message = "exit";
	
	public static void main(String[] args) throws IOException{
		Socket client = new Socket(Inet4Address.getByName(IP), PORT);
		
		System.out.println("Sending \"" + message + "\" to: " + IP + " : " + PORT);
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println(message);
		
		InputStreamReader isr = new InputStreamReader(client.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		String line = reader.readLine();
		if (!line.isEmpty()) {
			System.out.println("Response from Server: " + line);
		}	
		client.close();
	}
}
