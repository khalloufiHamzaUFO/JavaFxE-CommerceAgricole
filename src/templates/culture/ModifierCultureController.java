/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates.culture;


import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Culture;
import services.CultureCRUD;


public class ModifierCultureController implements Initializable {

    private TextField tfID;
    @FXML
    private TextField tfType;
    @FXML
    private DatePicker dtDate_planting;
    @FXML
    private TextField tfQuantite;
    @FXML
    private Button btnModifier;
    private TableColumn<Culture, String> Col_type;
    private TableColumn<Culture, Date> Col_date_planting;
    private TableColumn<Culture, Float> Col_quantite;
    private TableView<Culture> table_view;
    private Stage primarystage;
    @FXML
    private ImageView Im_agro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private Culture cultureAModifier;

    public void initData(Culture culture) {
        cultureAModifier = culture;
        tfType.setText(culture.getType());
        Date date = culture.getDate_planting();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDate();
        dtDate_planting.setValue(localDate);
        tfQuantite.setText(Float.toString(culture.getQuantite()));
    }


    @FXML
    private void ModifierCulture(ActionEvent event) {

        try {
            String type = tfType.getText();
            if (type.isEmpty()) {
                showError("Veuillez saisir un type");
                return;
            } else if (!type.matches("[a-zA-Z]+")) {
                showError("Le type doit contenir uniquement des lettres");
                return;
            } else if (type.length() < 2 || type.length() > 20) {
                showError("Le type doit avoir une longueur comprise entre 2 et 20 caractères");
                return;
            }

            LocalDate localDate = dtDate_planting.getValue();
            if (localDate == null) {
                showError("Veuillez saisir une date");
                return;
            } else if (localDate.isAfter(LocalDate.now())) {
                showError("La date doit être inférieure ou égale à la date d'aujourd'hui");
                return;
            }
            Date date_planting = java.sql.Date.valueOf(localDate);

            String quantiteStr = tfQuantite.getText();
            if (quantiteStr.isEmpty()) {
                showError("Veuillez saisir une quantité");
                return;
            }

            float quantite;
            try {
                quantite = Float.parseFloat(quantiteStr);
            } catch (NumberFormatException e) {
                showError("La quantité doit être un nombre décimal");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCulturee.fxml"));
            Parent root = loader.load();
            AfficherCultureeController afficherCultureController = loader.getController();

            CultureCRUD cc = new CultureCRUD();


            cultureAModifier.setType(type);
            cultureAModifier.setDate_planting(date_planting);
            cultureAModifier.setQuantite(quantite);
            cc.modifier(cultureAModifier);
            showSuccess("La culture a été modifiée avec succès");
            tfType.setText("");
            dtDate_planting.setValue(null);
            tfQuantite.setText("");
            Stage stage = (Stage) btnModifier.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la modification de la culture");
            e.printStackTrace();
        }

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    @SuppressWarnings("empty-statement")
    private void Retour(ActionEvent event) throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherCulturee.fxml"));
        ;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }


}





