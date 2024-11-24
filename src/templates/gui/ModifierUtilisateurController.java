package templates.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.user.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifierUtilisateurController implements Initializable {

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField MotDePassePasswordField;
    
    private Utilisateur utilisateur;
    private TextField rolestext;
   
    @FXML
    private TextField telFieldText;
    @FXML
    private ComboBox<String> roless;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
 ObservableList<String> rolesList = FXCollections.observableArrayList("USER_ROLES", "ADMIN_ROLES");
    roless.setItems(rolesList);
    }    
    
    
     public void initData(Utilisateur utilisateur) {
         this.utilisateur = utilisateur;
    nomTextField.setText(utilisateur.getNom());
    prenomTextField.setText(utilisateur.getPrenom());
    emailTextField.setText(utilisateur.getEmail());
    MotDePassePasswordField.setText(utilisateur.getPassword());
    roless.setValue(utilisateur.getRoles());
    telFieldText.setText(utilisateur.getTelephone());
//    cinFieldText1.setText(Integer.toString(utilisateur.getCin()));
        
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @FXML
    private void enregistrer(ActionEvent event) {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String email = emailTextField.getText();
        String motDePasse = MotDePassePasswordField.getText();
        String tel = telFieldText.getText();
       String roles = roless.getValue();
     //int cin = Integer.parseInt(cinFieldText1.getText()); // Ajouter le champ 'cin'


        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis");
            alert.showAndWait();
            return;
        }

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setPassword(motDePasse);
        utilisateur.setRoles(roles);
        utilisateur.setTelephone(tel);
 //       utilisateur.setCin(cin); // Mettre à jour le champ 'cin'
 

        ((Stage) nomTextField.getScene().getWindow()).close();
    }
@FXML
    private void annuler(ActionEvent event) {
        utilisateur = null;
        ((Stage) nomTextField.getScene().getWindow()).close();
    }

}

