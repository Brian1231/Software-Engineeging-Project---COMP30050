package client.java;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);
        label.setTextFill(Color.rgb(232, 142, 39));
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(e-> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->{
            answer = false;
            window.close();
        });

        VBox yLayout = new VBox(20);
        yLayout.setPadding(new Insets(20, 20, 20, 20));
        HBox xLayout = new HBox(20);
        yLayout.setAlignment(Pos.CENTER);
        xLayout.setAlignment(Pos.CENTER);
        xLayout.getChildren().addAll(yesButton,noButton);
        yLayout.getChildren().addAll(label, xLayout);

        Scene scene = new Scene(yLayout);
        scene.getStylesheets().add("/client/resources/css/alertBox.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
