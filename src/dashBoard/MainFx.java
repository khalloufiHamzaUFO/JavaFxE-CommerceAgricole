
package dashBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.net.http.WebSocket;

public class MainFx extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../dashBoard/Home.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../templates/event/AddPart.fxml"));


        primaryStage.getIcons().add(new Image("/image/bio.png"));
        primaryStage.setTitle("AgroEasy");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
