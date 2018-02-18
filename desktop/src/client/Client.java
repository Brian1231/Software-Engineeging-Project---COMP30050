package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Client extends Application {

	//final static String IP = "52.48.249.220";
	//final static int PORT = 8080;
	//final static String message = "roll";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("welcomeScreen.fxml"));
		primaryStage.setTitle("Panopoly");
		Scene scene = new Scene(root, 1200, 800);
		scene.getStylesheets().addAll(this.getClass().getResource("welcome.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) throws IOException{
		//Socket client = new Socket(Inet4Address.getByName(IP), PORT);
		//
		//System.out.println("Sending \"" + message + "\" to: " + IP + " : " + PORT);
		//PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		//out.println(message);
		
		//InputStreamReader isr = new InputStreamReader(client.getInputStream());
		//BufferedReader reader = new BufferedReader(isr);
		//String line = reader.readLine();
		//if (!line.isEmpty()) {
		//	System.out.println("Response from Server: " + line);
		//}
		//client.close();

		launch(args);
	}
}
