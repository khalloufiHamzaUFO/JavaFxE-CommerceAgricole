package templates.event;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Participation;
import services.PartService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddPartController implements Initializable {

    @FXML
    private AnchorPane modpan;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private Label t1;
    @FXML
    private TextField tt1;
    @FXML
    private TextField tt2;
    @FXML
    private Label t2;
    @FXML
    private Label t3;
    @FXML
    private Button bn1;
    @FXML
    private Button bn4;
    @FXML
    private Label t5;
    @FXML
    private TableView<Participation> tableau;
    @FXML
    private TableColumn<?, ?> trx1;
    @FXML
    private TableColumn<?, ?> trx2;
    @FXML
    private TableColumn<?, ?> trx3;
    @FXML
    private Button bn2;
    @FXML
    private Button bn3;
    PartService ps = new PartService();
    private ObservableList<Participation> L = ps.getAllPart();;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    public void update(){
        ObservableList <Participation> list = FXCollections.observableArrayList();
        for (Participation participation: L){
            list.add(participation);
        }
        tableau.setItems(list);
        trx1.setCellValueFactory(new PropertyValueFactory<>("id"));
        trx2.setCellValueFactory(new PropertyValueFactory<>("id_utilisateur_id"));
        trx3.setCellValueFactory(new PropertyValueFactory<>("evenement_id"));
    }

    @FXML
    private void ajouter(ActionEvent event) {
        int id_utilisateur_id=Integer.parseInt(tt1.getText());
        int evenement_id= Integer.parseInt(tt2.getText());
        String IdRegex = "^[0-900000]+$";
        Participation p = new Participation();

        p.setId_utilisateur_id(id_utilisateur_id);
        p.setEvenement_id(evenement_id);

        ps.ajouter(p);
    }




    @FXML
    private void modifier(ActionEvent event) {
        try {
            Participation selectedEvent = tableau.getSelectionModel().getSelectedItem();
            // Vérifier si l'événement a été trouvé, afficher un message d'erreur si ce n'est pas le cas
            if (selectedEvent == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun événement trouvé pour le modifier.");
                alert.showAndWait();
                return;
            }


            // Si l'événement a été trouvé, afficher les données de cet événement dans les champs de texte de la page modif.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifpart.fxml"));
            Parent root = loader.load();
            ModifpartController modifpartController = loader.getController();
            modifpartController.initializeData(selectedEvent);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Participation pt = modifpartController.getParticipation();
        } catch (IOException ex) {
            Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Participation selectedEvent = tableau.getSelectionModel().getSelectedItem();
        int a = selectedEvent.getId();
        System.out.println(a);
        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Sélectionnez une participation à supprimer");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet événement ?");
        alert.setContentText("La particiaption avec l'evenement d'id: " + selectedEvent.getId());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ps.supprimer(a);
            L = ps.getAll();
            ObservableList<Participation> list = FXCollections.observableArrayList();
            for (Participation participation : L) {
                list.add(participation);
            }
            tableau.setItems(list);
        }

    }


    @FXML
    public void handleRefresh(ActionEvent event) {
        update();
    }
    public static Parent loadFXML(String fxmlFilePath) {
        FXMLLoader loader = new FXMLLoader(AddEventController.class.getResource("AddPart.fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}