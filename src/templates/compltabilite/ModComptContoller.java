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

public class ModComptContoller implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfVal;

    private Comptabilite c = new Comptabilite();
    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setComptabilite(Comptabilite comptabilite) {
        this.c = comptabilite;
        Date date = comptabilite.getDate_comptabilite();
        double val = comptabilite.getValeur();
        LocalDate localDate = date.toLocalDate();
        tfDate.setValue(localDate);
        tfVal.setText(String.valueOf(val));
    }

    @FXML
    public void handleEdit() {
        LocalDate date = tfDate.getValue();
        String valStr = tfVal.getText();

        // Check for empty inputs
        if (date == null || valStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        // Check for non-negative value for tfVal
        float val = Float.parseFloat(valStr);
        if (val < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("La valeur ne doit pas être négative !");
            alert.showAndWait();
            return;
        }

        c.setDate_comptabilite(Date.valueOf(date));
        c.setValeur(val);

        try {
            comptabiliteCRUD.modifier(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Comptabilité modifiée avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de la comptabilité : " + e.getMessage());
            alert.showAndWait();
        }
    }

}
