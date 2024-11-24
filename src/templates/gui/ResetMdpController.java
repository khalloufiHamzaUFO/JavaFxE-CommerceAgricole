package templates.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ServiceUser;

import static templates.gui.LoginRegister.u;

public class ResetMdpController implements Initializable {

    @FXML
    private Button btnvalid;
    @FXML
    private PasswordField pwdr;
    @FXML
    private PasswordField pwdn;


    private String email;
    @FXML
    private PasswordField pwda;


    ServiceUser su = new ServiceUser();
    @FXML
    private Button retoubtn;

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void validerm(ActionEvent event) throws SQLException {

        if (pwda.getText().equals(u.getPassword()) && pwdn.getText().equals(pwdr.getText())) {
            if (su.resetPassword(pwdn.getText(), u.getEmail())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme" + u.getNom()+ " " +u.getPrenom()+ " " + u.getEmail() + " Votre mot de passe a été bien modifier !", ButtonType.CLOSE);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " il ya un petit probleme ressayer plus tard !", ButtonType.CLOSE);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, " l'une des mot de passes ne correspand pas merci de ressayer !", ButtonType.CLOSE);
            alert.show();
        }

    }

    @FXML
    private void retour(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("UpdateProfile.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
    

