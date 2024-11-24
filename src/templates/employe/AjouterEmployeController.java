package templates.employe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Employe;
import services.EmployeCRUD;


public class AjouterEmployeController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfCin;
    @FXML
    private Button btnAjouter;
    private TableColumn<Employe, Integer> col_id;
    private TableColumn<Employe, String> col_nom;
    private TableColumn<Employe, String> col_prenom;
    private TableColumn<Employe, String> col_cin;
    private TableColumn<Employe, String> col_numtelephone;
    private TableView<Employe> table_view;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfSalaire;
    @FXML
    private TextField tfNumtelephone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void AddEmploye(ActionEvent event) {
        try {
            String nom = tfNom.getText();
            if (nom.isEmpty()) {
                showError("Veuillez saisir un nom");
                return;
            } else if (!nom.matches("[a-zA-Z]+")) {
                showError("Le nom doit contenir uniquement des lettres");
                return;
            } else if (nom.length() < 2 || nom.length() > 40) {
                showError("Le nom doit avoir une longueur comprise entre 2 et 40 caractères");
                return;
            }

            String prenom = tfPrenom.getText();
            if (prenom.isEmpty()) {
                showError("Veuillez saisir un prénom");
                return;
            } else if (!prenom.matches("[a-zA-Z]+")) {
                showError("Le prénom doit contenir uniquement des lettres");
                return;
            } else if (prenom.length() < 2 || prenom.length() > 40) {
                showError("Le prénom doit avoir une longueur comprise entre 2 et 40 caractères");
                return;
            }
            String CinStr = tfCin.getText();
            if (CinStr.isEmpty()) {
                showError("Veuillez saisir une cin");
                return;
            } else if (CinStr.length() != 8) {
                showError("Le cin doit être composé de 8 chiffres");
                return;
            }
            int cin;
            try {
                cin = Integer.parseInt(CinStr);
            } catch (NumberFormatException e) {
                showError("Le cin doit être un nombre entier");
                return;
            }

            String AgeStr = tfAge.getText();
            if (AgeStr.isEmpty()) {
                showError("Veuillez saisir un age");
                return;
            } else if (AgeStr.length() != 2) {
                showError("L'âge doit être composé de 2 chiffres");
                return;
            }
            int age;
            try {
                age = Integer.parseInt(AgeStr);
            } catch (NumberFormatException e) {
                showError("L'age doit être un nombre entier");
                return;
            }
            if (age < 18) {
                showError("L'âge doit être supérieur ou égal à 18 ans");
                return;
            }
            String SalaireStr = tfSalaire.getText();
            if (SalaireStr.isEmpty()) {
                showError("Veuillez saisir une salaire");
                return;
            }
            float salaire = Float.parseFloat(SalaireStr);
            try {
                cin = Integer.parseInt(CinStr);
            } catch (NumberFormatException e) {
                showError("La salaire  doit être un nombre entier");
                return;
            }
            String NumtelephoneStr = tfNumtelephone.getText();
            if (NumtelephoneStr.isEmpty()) {
                showError("Veuillez saisir un numero de telephone");
                return;
            } else if (NumtelephoneStr.length() != 8) {
                showError("Le numero de telephone doit être composé de 8 chiffres");
                return;
            }
            int numtelephone;
            try {
                numtelephone = Integer.parseInt(NumtelephoneStr);
            } catch (NumberFormatException e) {
                showError("Le numero de telephone doit être un nombre entier");
                return;
            }
            Employe p = new Employe(nom, prenom, String.valueOf(cin), (int) age, salaire, numtelephone);

            EmployeCRUD pcd = new EmployeCRUD();
            Employe existant = pcd.rechercherParCin(String.valueOf(cin));
            if (existant != null) {
                showError("Cet employé existe déjà");
                return;
            }
            pcd.ajouter2(p);
            showSuccess("L'employe a été ajoutée avec succès");
        } catch (NumberFormatException e) {
            showError("Le format du nom ou de  CIN est incorrect");
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

    private void ShowEmploye(ActionEvent event) {

        EmployeCRUD pcd = new EmployeCRUD();
        List<Employe> Employes = pcd.getAll();
        table_view.getItems().clear();
        table_view.getItems().addAll(Employes);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEmploye.fxml"));
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

} 

