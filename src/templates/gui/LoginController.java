package templates.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.user.Utilisateur;
import services.ServiceUser;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LoginController implements Initializable {

    @FXML
    private BorderPane loginform;
    @FXML
    private TextField emailc;
    @FXML
    private TextField pwdc;
    @FXML
    private Button logincbtn;

    ServiceUser us = new ServiceUser();
    @FXML
    private Button signupbtn;
    @FXML
    private Label pwdbtn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void login(ActionEvent event) {
        String email = emailc.getText();
        String password = pwdc.getText();
        Utilisateur loggedInUser = authenticate(email, password);
        if (loggedInUser != null) {
            try {
                ServiceUser serviceUser = new ServiceUser();
                loggedInUser = serviceUser.getUserById(loggedInUser.getId());
                File sessionFile = new File("session.txt");

                // Save user ID to session file
                try (PrintWriter writer = new PrintWriter(sessionFile)) {
                    writer.println(loggedInUser.getId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(loggedInUser.getId());
                if (loggedInUser.getRoles().equals("[\"ROLE_Admin\"]")) {
                    // Load the gestion utilisateur scene
                    Stage stage = (Stage) logincbtn.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("../../dashBoard/Home.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Stage stage = (Stage) logincbtn.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("../../dashBoardFront/market.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Utilisateur authenticate(String email, String password) {
        Utilisateur u = us.Signin(email, password);
        return u;
    }

    private void goToInscription(MouseEvent event) {
        try {
            Stage stage = (Stage) logincbtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("GestionUtilisateur.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void inscription(ActionEvent event) {
        try {
            Stage currentStage = (Stage) logincbtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpK.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            currentStage.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @FXML
    private void goToEmailVerifie() {
        try {
            Stage stage = (Stage) logincbtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("VerificationEmail.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
