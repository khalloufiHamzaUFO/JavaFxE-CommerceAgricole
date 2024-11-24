package templates.facture;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Comptabilite;
import models.Facture;
import services.ComptabiliteCRUD;
import services.FactureCRUD;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddFacture implements Initializable {

    @FXML
    private ComboBox<Comptabilite> cbComt;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private DatePicker tdDate;

    @FXML
    private TextField tfVal;

    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();
    FactureCRUD factureCRUD = new FactureCRUD();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbComt.getItems().setAll(comptabiliteCRUD.getAll());
        cbType.getItems().addAll("achat", "vente");

    }

    @FXML
    public void handleAdd() {
        String type = cbType.getValue();
        Comptabilite comptabilite = cbComt.getValue();
        LocalDate date = tdDate.getValue();
        Date datee = Date.valueOf(date);
        String valeurStr = tfVal.getText();

        if (type == null || comptabilite == null || date == null || valeurStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
            return;
        }

        float valeur;
        try {
            valeur = Float.parseFloat(valeurStr);
            if (valeur < 0) {
                JOptionPane.showMessageDialog(null, "Valeur must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid valeur");
            return;
        }

        Facture newFacture = new Facture(datee, valeur, type, comptabilite);
        factureCRUD.ajouterFacture(newFacture);

        JOptionPane.showMessageDialog(null, "Facture added successfully");
        tfVal.clear();
    }






}
