package templates.equipement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import models.Employe;
import models.Equipement;
import services.EmployeCRUD;
import services.EquipementCRUD;

public class ModifierEquipementController implements Initializable {

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfMarque;
    @FXML
    private TextField tfEtat;
    @FXML
    private TextField tfMatricule;
    @FXML
    private CheckBox tfDisponnible1;
    @FXML
    private CheckBox tfDisponnible2;
    @FXML
    private ComboBox<Employe> tfID_emp;
    @FXML
    private Button btnModifier;
    private Equipement EquipmentAModifier;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Employe> Employes = new EmployeCRUD().getAll();
        tfID_emp.setItems(FXCollections.observableArrayList(Employes));

    }

    public void initData(Equipement equipement) {
        EquipmentAModifier = equipement;
        tfID_emp.setValue(equipement.getEmploye_());
        tfEtat.setText(String.valueOf(equipement.getEtat()));
        tfMatricule.setText(String.valueOf(equipement.getMatricule()));
        tfType.setText(equipement.getType());
        tfMarque.setText(equipement.getMarque());

    }


    @FXML
    private void ModifierEquipment(ActionEvent event) {
        try {
            Employe employe = tfID_emp.getValue();

            if (employe == null) {
                showError("Veuillez selectionner un employé");
                return;
            }

            String Type = tfType.getText();
            if (Type.isEmpty()) {
                showError("Veuillez saisir un Type");
                return;

            } else if (Type.length() < 2 || Type.length() > 20) {
                showError("Le Type doit avoir une longueur comprise entre 2 et 20 caractères");
                return;
            }
            String Marque = tfMarque.getText();
            if (Marque.isEmpty()) {
                showError("Veuillez saisir un Marque");

                return;
            } else if (Marque.length() < 2 || Marque.length() > 20) {
                showError("Le Marque doit avoir une longueur comprise entre 2 et 20 caractères");
                return;
            }
            String matriculeStr = tfMatricule.getText();
            if (matriculeStr.isEmpty()) {
                showError("Veuillez saisir une matricule");
                return;
            }
            float matricule;
            try {
                matricule = Float.parseFloat(matriculeStr);
            } catch (NumberFormatException e) {
                showError("La matricule doit être un nombre entier");
                return;
            }
            String etat = tfEtat.getText();
            if (etat.isEmpty()) {
                showError("Veuillez saisir  l'etat de l'equipment");
                return;
            } else if (!etat.matches("[a-zA-Z]+")) {
                showError("L'etat doit contenir uniquement des lettres");
                return;
            } else if (etat.length() < 2 || etat.length() > 20) {
                showError("Le Type doit avoir une longueur comprise entre 2 et 20 caractères");
                return;
            }
            if (matriculeStr.isEmpty()) {
                showError("Veuillez saisir le numero de  matricule");
                return;
            } else if (matriculeStr.length() < 8 || matriculeStr.length() > 8) {
                showError("la matricule doit avoir une longueur de 8 caractères");
                return;
            }
            Boolean disponnible;
            if (!tfDisponnible1.isSelected() && !tfDisponnible2.isSelected()) {
                showError("Veuillez sélectionner si l'équipement est disponible ou non");
                return;
            } else if (tfDisponnible1.isSelected() && tfDisponnible2.isSelected()) {
                showError("Veuillez sélectionner un seul état de disponibilité pour l'équipement");
                return;
            } else {
                disponnible = tfDisponnible1.isSelected();
            }
            EquipementCRUD ecd = new EquipementCRUD();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEquipment.fxml"));
            AfficherEquipmentController afficherEquipmentController = loader.getController();
            EquipementCRUD ec = new EquipementCRUD();

            EquipmentAModifier.setEmploye(employe);
            EquipmentAModifier.setType(Type);
            EquipmentAModifier.setMarque(Marque);
            EquipmentAModifier.setDisponnible(disponnible);
            EquipmentAModifier.setEtat(etat);
            EquipmentAModifier.setMatricule(matriculeStr);
            ec.modifier2(EquipmentAModifier);

            System.out.println(EquipmentAModifier.toString());
            showSuccess("L'equipment est modifiée avec succès");
            tfID_emp.setValue(null);
            tfType.setText("");
            tfMarque.setText("");
            tfDisponnible1.setText("");
            tfDisponnible2.setText("");
            tfEtat.setText("");
            tfMatricule.setText("");
        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la modification de la liste equipement");
            e.printStackTrace();
        }

    }


    @FXML
    private void btnretour(ActionEvent event) throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEquipment.fxml"));
        ;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
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
}
