package templates.compltabilite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Categorie;
import models.Comptabilite;
import services.ComptabiliteCRUD;
import templates.gui.ResetMdpController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherComptController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnMod;

    @FXML
    private TableColumn<Comptabilite, Date> colDate;

    @FXML
    private TableColumn<Comptabilite, Float> ColVal;

    @FXML
    private TableView<Comptabilite> tvCompt;

    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();
    private ObservableList<Comptabilite> observableListCategorie = comptabiliteCRUD.getAll();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    public void update(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_comptabilite"));
        ColVal.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        tvCompt.setItems(observableListCategorie);
    }

    @FXML
    public void handleDelete() {
        Comptabilite selectedComptabilite = tvCompt.getSelectionModel().getSelectedItem();
        if (selectedComptabilite != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete selected item?");
            alert.setContentText("Are you sure you want to delete the selected item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                comptabiliteCRUD.supprimer(selectedComptabilite.getId());
                tvCompt.getItems().remove(selectedComptabilite);
            }
        }
    }

    @FXML
    public void handleEdit() {
        Comptabilite selectedComptabilite = tvCompt.getSelectionModel().getSelectedItem();
        if (selectedComptabilite != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModCompt.fxml"));
                Parent root = loader.load();

                ModComptContoller editComptController = loader.getController();
                editComptController.setComptabilite(selectedComptabilite);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Comptabilite");
                stage.showAndWait();
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void Calcul(ActionEvent event) {
        FXMLLoader LOADER = new FXMLLoader(getClass().getResource("CalculComptabilite.fxml"));
        try {
            Parent root = LOADER.load();
            Scene sc = new Scene(root);
            CalculComtController cntr = LOADER.getController();
            Stage newWindow = new Stage();
            newWindow.setScene(sc);
            newWindow.show();
        } catch (IOException ex) {

        }
    }

    @FXML
    public void handleSearch() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<Comptabilite> filteredList = observableListCategorie.stream()
                .filter(c -> c.getDate_comptabilite().toString().toLowerCase().contains(searchQuery) ||
                        String.valueOf(c.getValeur()).toLowerCase().contains(searchQuery))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tvCompt.setItems(filteredList);
    }



}
