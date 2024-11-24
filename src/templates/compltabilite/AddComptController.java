package templates.compltabilite;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Comptabilite;
import services.ComptabiliteCRUD;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddComptController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfVal;

    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addComptabilite() {
        LocalDate date = tfDate.getValue();
        Date dateC = Date.valueOf(date);
        String valStr = tfVal.getText().trim();
        if (valStr.isEmpty()) {
            showAlert("Value cannot be empty.");
            return;
        }
        float val = 0;
        try {
            val = Float.parseFloat(valStr);
            if (val < 0) {
                showAlert("Value must be non-negative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Value must be a valid decimal number.");
            return;
        }
        Comptabilite comptabilite = new Comptabilite(dateC, val);
        comptabiliteCRUD.ajouterComptabilite(comptabilite);
        showPass("Ajout avec succee !!");

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showPass(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
