package controllers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
///
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Categorie;
import models.Produit;
import services.ServiceCategorie;
import services.ServiceProduit;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Timer;
import java.util.stream.Collectors;

public class ProduitController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnMod;

    @FXML
    private Label lblNdispo;

    @FXML
    private Label lbldispo;

    @FXML
    private TableColumn<Produit, String> colCategorie;

    @FXML
    private TableColumn<Produit, String> colDescription;

    @FXML
    private TableColumn<Produit, String> colImage;

    @FXML
    private TableColumn<Produit, String> colNom;

    @FXML
    private TableColumn<Produit, Float> colPrix;

    @FXML
    private TableColumn<Produit, Boolean> colStatut;

    @FXML
    private TableView<Produit> tvProduit;

    @FXML
    private TextField tfSearch;

    @FXML
    private ComboBox<String> cbCategorie;

    static ServiceCategorie serviceCategorie = new ServiceCategorie();
    static ServiceProduit serviceProduit = new ServiceProduit();
    static ObservableList<Produit> observableListProduits = serviceProduit.getAll() ;
    private ObservableList<Produit> produits = FXCollections.observableArrayList();

    int index = -1;
    int d = 0;
    int n = 0;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Categorie> categories = serviceCategorie.getAll();
        ObservableList<String> categoryLabels = FXCollections.observableArrayList(
                categories.stream().map(Categorie::getLabel).collect(Collectors.toList()));
        categoryLabels.add("All products");
        cbCategorie.setItems(categoryLabels);
        cbCategorie.setValue("All products");
        cbCategorie.valueProperty().addListener((observable, oldValue, newValue) -> {
            onChoicePicked();
        });
        updateTable();
        search_produit();

        produits.addAll(serviceProduit.getAll());
        produits.addListener((ListChangeListener<Produit>) c -> {
                while (c.next()) {
                    if (c.wasAdded()) {
                            produits.addAll(c.getAddedSubList());
                        observableListProduits= serviceProduit.getAll();
                        updateTable();
                        search_produit();
                    } else if (c.wasRemoved()) {
                        produits.removeAll(c.getRemoved());
                        observableListProduits= serviceProduit.getAll();
                        updateTable();
                        search_produit();
                    } else if (c.wasUpdated()) {
                        observableListProduits= serviceProduit.getAll();
                        updateTable();
                        search_produit();                    }
                    System.out.println("S");
                }
                updateTable();
                search_produit();
        });

    }


    private boolean isListContentChanged(List<Produit> newList) {
        if (newList.size() != observableListProduits.size()) {
            return true;
        }
        for (int i = 0; i < newList.size(); i++) {
            if (!newList.get(i).equals(observableListProduits.get(i))) {
                return true;
            }
        }
        return false;
    }


    @FXML
    public void onChoicePicked() {
        String selectedLabel = cbCategorie.getValue();
        if (selectedLabel == null || selectedLabel=="All products") {
            observableListProduits.setAll(serviceProduit.getAll());
        } else {
            List<Produit> filteredProduits = serviceProduit.getAll().stream()
                    .filter(p -> selectedLabel.equals(p.getCategorie().getLabel()))
                    .collect(Collectors.toList());
            observableListProduits.clear();
            observableListProduits.setAll(filteredProduits);
        }
        updateTable();
        search_produit();
    }
    public void refresh(){
        updateTable();
    }

    public void updateTable() {
        colNom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        colCategorie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie().getLabel()));
        colPrix.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
        colStatut.setCellValueFactory(new PropertyValueFactory<Produit, Boolean>("status"));
        tvProduit.setRowFactory(tv -> new TableRow<Produit>() {
            @Override
            public void updateItem(Produit item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    boolean isAvailable = item.getStatus(); // assuming the column type is BooleanProperty or boolean
                    if (isAvailable) {
                        setStyle("-fx-background-color: #E5F9DB;");
                    } else {
                        setStyle("-fx-background-color: #F2B6A0;");
                    }
                }
            }
        });

        colImage.setCellValueFactory(new PropertyValueFactory<Produit, String>("image"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colImage.setCellFactory(col -> new ImageTableCell());
        tvProduit.setItems(this.observableListProduits);
        d= 0;
        n= 0;

        for (int i = 0; i <observableListProduits.size() ; i++) {
            if(observableListProduits.get(i).getStatus())
                d+=1;
            else
                n+=1;
        }
        lblNdispo.setText(Integer.toString(d));
        lbldispo.setText(Integer.toString(n));
    }


    public static class ImageTableCell extends TableCell<Produit, String> {

        private final ImageView imageView = new ImageView();
        private final double imageSize = 50;

        public ImageTableCell() {
            setAlignment(Pos.CENTER);
            setGraphic(imageView);
        }

        @Override
        protected void updateItem(String imagePath, boolean empty) {
            super.updateItem(imagePath, empty);
            if (empty || imagePath == null) {
                imageView.setImage(null);
                setText(null);
                setGraphic(null);
            } else {
                Path destinationPath = Paths.get("src", "image", imagePath);
                File file = destinationPath.toFile();
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(imageSize);
                imageView.setFitHeight(imageSize);
                setGraphic(imageView);
            }
        }
    }
    public void getSelected() {
        index = tvProduit.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        updateTable();
        search_produit();
    }

    @FXML
    void search_produit() {
        colNom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        colCategorie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie().getLabel()));
        colPrix.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
        colStatut.setCellValueFactory(new PropertyValueFactory<Produit, Boolean>("status"));
        colImage.setCellValueFactory(new PropertyValueFactory<Produit, String>("image"));
        tvProduit.setItems(observableListProduits);
        FilteredList<Produit> filteredData = new FilteredList<>(observableListProduits, b->true);
        tfSearch.textProperty().addListener((observable,oldValue,newValue)-> {
            filteredData.setPredicate(produit-> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf((produit.getId())).contains(lowerCaseFilter)){
                    return true;
                }else if (produit.getDescription().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if (produit.getCategorie().getLabel().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (produit.getNom().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if (String.valueOf(produit.getPrix()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (produit.getImage().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(produit.getStatus()).toLowerCase().contains(lowerCaseFilter)){
                return true;
                }
                else
                    return false ;
            });
        });
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvProduit.comparatorProperty());
        tvProduit.setItems(sortedData);
    }

    @FXML
    void handleButtonUpdate() throws IOException {
        Produit selectedProduit = tvProduit.getSelectionModel().getSelectedItem();
        if (selectedProduit == null) {
            infomat("Veuillez sélectionner un produit à modifier.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../templates/produit/formModif.fxml"));
        AnchorPane formProduit = loader.load();

        FormController controller = loader.getController();
        controller.setSelectedProduit(selectedProduit);

        Scene formScene = new Scene(formProduit);
        Stage newStage = new Stage();
        newStage.setScene(formScene);
        newStage.show();
    }


    @FXML
    public void handleButtonSupprimer() {
        int selectedIndex = tvProduit.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Produit selectedProduit = tvProduit.getItems().get(selectedIndex);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer le produit \"" + selectedProduit.getNom() + "\" ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ServiceProduit serviceProduit = new ServiceProduit();
                serviceProduit.supprimer(selectedProduit.getId());
                updateTable();
                search_produit();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucun produit sélectionné");
            alert.setContentText("Veuillez sélectionner un produit dans la table.");
            alert.showAndWait();
        }
    }



    public void infomat(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Care");
        alert.setContentText(s);
        alert.showAndWait();
    }


    public void error(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Attetion !");
        alert.setContentText(s);
        alert.showAndWait();
    }
}
