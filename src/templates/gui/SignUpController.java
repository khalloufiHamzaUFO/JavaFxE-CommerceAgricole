package templates.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.user.Utilisateur;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private TextField noms;
    @FXML
    private TextField prenoms;
    @FXML
    private TextField cins;
    @FXML
    private TextField tels;

    @FXML
    private PasswordField pwds;
    @FXML
    private Button signupbtn;


    ServiceUser us = new ServiceUser();
    @FXML
    private TextField emails;
    @FXML
    private ComboBox<String> roless;


    private Connection cnx;
    private PreparedStatement prepare;
    private ResultSet result;
    ServiceUser su = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajout des options possibles pour le choix de rôle

        roless.getItems().addAll( "[\"ROLE_Admin\"]", "[\"ROLE_USER\"]");
    }

    @FXML
    private void navigateToLogin(ActionEvent event) {
        try {
            // Close current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Load Login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginK.fxml"));
            Parent root = loader.load();

            // Create new window and show Login scene
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void signup(ActionEvent event) throws SQLException {

        Utilisateur u = new Utilisateur();


        String nom = noms.getText();
        String prenom = prenoms.getText();
        String email = emails.getText();
        String cin = cins.getText();
        String tel = tels.getText();
        String password = pwds.getText();
        String roles = roless.getValue();


        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String nameRegex = "^[a-zA-Z]+$";
        String phoneRegex = "^(2|5|9)[0-9]{7}$";

        if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email invalide !");
            alert.showAndWait();
        } else if ((password.length() < 8)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Mot de passe  doit etre supérieure à 8 caractère  !");
            alert.showAndWait();
        } else if (!nom.matches(nameRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("nom invalide !");
            alert.showAndWait();
        } else if (!prenom.matches(nameRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("prenom invalide !");
            alert.showAndWait();
        } else if (!tel.matches(phoneRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("numéro tel invalide !");
            alert.showAndWait();
        } else if (cin.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("adresse invalide !");
            alert.showAndWait();
        } else {
            u.setEmail(email);
            u.setPassword(password);
            u.setNom(nom);
            u.setPrenom(prenom);
            u.setRoles(roles);
            u.setCin(Integer.parseInt(cin));
            u.setTelephone(tel);

            us.ajouter(u);
            try {
                Stage stage = (Stage) signupbtn.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("VerificationEmail.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
