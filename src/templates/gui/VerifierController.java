/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class VerifierController implements Initializable {

    @FXML
    private TextField codev;
    @FXML
    private Button validerbtn;
    
    Random rand = new Random();
    int codeVerification = rand.nextInt((1000) + (9999));
    String codeVerif = String.valueOf(codeVerification);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void valider(ActionEvent event) {
        String codeV = codev.getText();
        if (!codeV.equals(this.codeVerif)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Code invalide !");
            alert.showAndWait();
        } else {
           try{
                            Stage stage = (Stage) validerbtn.getScene().getWindow();
                            Parent root =FXMLLoader.load(getClass().getResource("login.fxml"));
                            Scene scene = new Scene(root );
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                    }catch(IOException ex){
                            System.out.println(ex.getMessage());
                    }
        }
    }
    
}
