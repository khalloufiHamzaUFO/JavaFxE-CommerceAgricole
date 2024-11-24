package templates.culture;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AjouterCultureController implements Initializable {

    private TextField tfID;
    @FXML
    private TextField tfType;
    @FXML
    private DatePicker dtDate_planting;
    @FXML
    private TextField tfQuantite;
    private TableColumn<Culture, String> Col_type;
    private TableColumn<Culture, Date> Col_date_planting;
    private TableColumn<Culture, Float> Col_quantite;
    private TableView<Culture> table_view;
     private Stage primarystage;
    @FXML
    private ImageView Im_agro;
    @FXML
    private Button btnAjouter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void AddCulture(ActionEvent event) {
  
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
       // Date date_planting = java.sql.Date.valueOf(dtDate_planting.getValue());
        String quantiteStr = tfQuantite.getText();
        if (quantiteStr.isEmpty()) {
            showError("Veuillez saisir une quantité");
            return;
        }
       // float quantite = Float.parseFloat(quantiteStr);
        float quantite;
        try {
            quantite = Float.parseFloat(quantiteStr);
        } catch (NumberFormatException e) {
            showError("La quantité doit être un nombre décimal");
            return;
        }
       
        //float quantite = Float.parseFloat(tfQuantite.getText());
 
        // Créer un objet Culture avec les valeurs entrées
        Culture c = new Culture( type, date_planting, quantite);

        // Insérer l'objet Culture dans la base de données
        CultureCRUD cc = new CultureCRUD();
        cc.ajouter(c);
        showSuccess("La culture a été ajoutée avec succès");
        } catch (Exception e) {
        showError("Une erreur s'est produite lors de l'ajout de la culture");
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
    private void Retour(ActionEvent event) throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherCulturee.fxml"));;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }








    
}





