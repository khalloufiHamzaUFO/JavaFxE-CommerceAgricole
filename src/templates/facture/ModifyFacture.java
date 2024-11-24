package templates.facture;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Comptabilite;
import models.Facture;
import services.ComptabiliteCRUD;
import services.FactureCRUD;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyFacture implements Initializable {

    @FXML
    private ComboBox<Comptabilite> cbComt;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private DatePicker tdDate;

    @FXML
    private TextField tfVal;

    private Facture selectedFacture;
    FactureCRUD factureCRUD = new FactureCRUD();
    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbComt.getItems().setAll(comptabiliteCRUD.getAll());
        cbType.getItems().addAll("achat", "vente");
    }

    public void initData(Facture facture) {
        selectedFacture = facture;
        cbComt.setValue(facture.getComptabilite());
        cbType.setValue(facture.getType());
        LocalDate localDate = LocalDate.now(); // create a LocalDate object
        tdDate.setValue(localDate);
        tfVal.setText(String.valueOf(facture.getMontant_total()));    }

    public void updateFacture() {
        // Check that all required fields are filled in
        if (cbComt.getValue() == null || cbType.getValue() == null || tdDate.getValue() == null || tfVal.getText().isEmpty()) {
            // Display an error message and return without updating the facture
            // (You could also disable the update button until all required fields are filled in, if you prefer)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Check that the montant is not less than 0
        double montant = Double.parseDouble(tfVal.getText());
        if (montant < 0) {
            // Display an error message and return without updating the facture
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The montant cannot be less than 0.");
            alert.showAndWait();
            return;
        }

        // Update the selected facture with the new values
        selectedFacture.setComptabilite(cbComt.getValue());
        selectedFacture.setType(cbType.getValue());
        LocalDate date = tdDate.getValue();
        java.sql.Date datee = Date.valueOf(date);
        selectedFacture.setDate(datee);
        selectedFacture.setMontant_total(Float.parseFloat(tfVal.getText()));

        // Call the update method from the FactureCrud class to save the changes to the database
        factureCRUD.updateFacture(selectedFacture);

        // Display a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The facture has been updated successfully.");
        alert.showAndWait();
    }



}
