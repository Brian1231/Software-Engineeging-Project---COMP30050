package client.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RulesPageController {

	@FXML
	public BorderPane base;
	public Button backButton;
	public TextArea rulesArea;

	public void initialize(){
		loadRules();
	}

	// Links back to Welcome Screen
	public void onBackClick(ActionEvent event){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/welcomeScreen.fxml"));
		Parent welcome = null;
		try {
			welcome =  loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load welcome page");
		}

		Scene welcomeScene = new Scene(welcome);
		welcomeScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/welcome.css").toExternalForm());
		Stage welcomeStage = (Stage)((Node) event.getSource()).getScene().getWindow();
		welcomeStage.setScene(welcomeScene);
	}

	// Loads Game Rules from text files and ands them to the Rule Page
	private void loadRules(){
		try(
				InputStream in = this.getClass().getResourceAsStream("/client/resources/rules.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(in))){
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			rulesArea.appendText(everything);
		}catch (Exception e){
			System.out.println("Could not load text file.");
			e.printStackTrace();
		}
	}
}
