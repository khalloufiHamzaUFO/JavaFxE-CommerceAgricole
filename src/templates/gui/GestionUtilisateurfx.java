/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 *
 * @author user
 */
public class GestionUtilisateurfx extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       try{
             Parent root = FXMLLoader.load(getClass().getResource("GestionUtilisateur.fxml"));
              Scene scene = new Scene(root);
              primaryStage.setTitle("Bienvenu a AgroEasy");
              primaryStage.setScene(scene);
              primaryStage.show();
                
            } catch (IOException ex) {
               System.out.println(ex.getMessage());
            }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
