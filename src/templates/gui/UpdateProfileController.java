package templates.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.user.Utilisateur;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static templates.gui.LoginRegister.u;

public class UpdateProfileController implements Initializable {

    @FXML
    private Button editeP;
    @FXML
    private Button pwdc;
    @FXML
    private Button btnretour;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telFieldText;
    @FXML
    private Button bntsave;

    ServiceUser su = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        emailTextField.setText(u.getEmail());
        nomTextField.setText(u.getNom());
        prenomTextField.setText(u.getPrenom());
        telFieldText.setText(u.getTelephone());
    }

    @FXML
    public void UpdateProfile(MouseEvent event) throws IOException, Exception {
        FXMLLoader LOADER = new FXMLLoader(getClass().getResource("UpdateProfile.fxml"));
        try {
            Parent root = LOADER.load();
            Scene sc = new Scene(root);
            UpdateProfileController cntr = LOADER.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(sc);
            window.show();
        } catch (IOException ex) {

        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
       
FXMLLoader loader = new FXMLLoader ();//creation de FXMLLoader 
                            loader.setLocation(getClass().getResource("Front.fxml")); //emplacement du fichier fxml 
                            try {
                                loader.load();
                            } catch (Exception ex) {
                               System.err.println(ex.getMessage());
                            }
                          
                            Parent parent = loader.getRoot(); 
                            Stage stage = new Stage(); //affichage de la fenetre 
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

        }

    

    @FXML
    private void Modifier(ActionEvent event) {
        Utilisateur u = new Utilisateur(nomTextField.getText(), prenomTextField.getText(), telFieldText.getText(), emailTextField.getText());
        try {
            su.modifier1(u);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme " + nomTextField.getText() + " " + prenomTextField.getText() + " " + " Vos donnés personnelles sont modifiées !", ButtonType.CLOSE);
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a un petit problème, réessayez plus tard !", ButtonType.CLOSE);
            alert.show();
        }

    }


    @FXML
    private void ResetPassword(ActionEvent event) {
        FXMLLoader LOADER = new FXMLLoader(getClass().getResource("ResetMdp.fxml"));
        try {
            Parent root = LOADER.load();
            Scene sc = new Scene(root);
            ResetMdpController cntr = LOADER.getController();
            Stage newWindow = new Stage();
            newWindow.setScene(sc);
            newWindow.show();
        } catch (IOException ex) {

        }
    }
}
