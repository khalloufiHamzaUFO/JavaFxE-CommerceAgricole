package dashBoardFront;

import com.google.zxing.WriterException;
import controllers.QrCodeGng;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Categorie;
import models.Produit;
import services.ServiceCategorie;
import services.ServiceProduit;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MarketController implements Initializable {

    @FXML
    private Button logoutbtn;

    @FXML
    private ComboBox<String> choiceCategorie;

    @FXML
    private Button btnSearch;

    @FXML
    private VBox chosenProduitCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView imageViewProduit;

    @FXML
    private ImageView imageViewQR;

    @FXML
    private Label lblCategorie;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNom;

    @FXML
    private Label lblPrix;

    @FXML
    private Label lblStatus;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField tfSearch;

    Random random = new Random();

    private List<Produit> produits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    ServiceProduit serviceProduit = new ServiceProduit();
    private ObservableList<Produit> observableListProduits = serviceProduit.getAll() ;
    ServiceCategorie serviceCategorie = new ServiceCategorie();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Categorie> categories = serviceCategorie.getAll();
        ObservableList<String> categoryLabels = FXCollections.observableArrayList(
                categories.stream().map(Categorie::getLabel).collect(Collectors.toList()));
        categoryLabels.add("All products");
        choiceCategorie.setItems(categoryLabels);
        produits.addAll(observableListProduits);
        if (produits.size() > 0) {
            setChosenProduitCard(produits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    setChosenProduitCard(produit);
                }

                @Override
                public void onCategoryListener() {
                    String selectedCategory = choiceCategorie.getValue();

                    if (selectedCategory.equals("All products")) {
                        observableListProduits.setAll(produits);
                    } else {
                        List<Produit> filteredProducts = produits.stream()
                                .filter(produit -> produit.getCategorie().getLabel().equals(selectedCategory))
                                .collect(Collectors.toList());
                        observableListProduits.setAll(filteredProducts);
                    }
                }
            };
        }
        produits.clear();
        produits.addAll(observableListProduits);
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/dashBoardFront/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChoice() {
        String selectedCategory = choiceCategorie.getValue();

        List<Produit> filteredProducts;

        if ( selectedCategory==null || selectedCategory.equals("All products")) {
            filteredProducts = new ArrayList<>(produits);
        } else {
            filteredProducts = produits.stream()
                    .filter(produit -> produit.getCategorie().getLabel().equals(selectedCategory))
                    .collect(Collectors.toList());
        }

        String searchTerm = tfSearch.getText().toLowerCase();
        if (!searchTerm.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                    .filter(produit -> produit.getNom().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
        }

        observableListProduits.setAll(filteredProducts);

        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (Produit produit : filteredProducts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/dashBoardFront/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produit, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static final String CURRENCY = "dt";

    @FXML
    public void handleSearch() {
        handleChoice();
    }

    private void setChosenProduitCard(Produit produit) {
        lblNom.setText(produit.getNom());
        lblPrix.setText(produit.getPrix() + CURRENCY );
        lblCategorie.setText(produit.getCategorie().getLabel());
        lblDescription.setText(produit.getDescription());
        lblStatus.setText(produit.getStatus() ? "disponible" : "pas disponible");

        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/"+produit.getImage())));
        imageViewProduit.setImage(image);

        Color randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        chosenProduitCard.setBackground(new Background(new BackgroundFill(randomColor, new CornerRadii(30), null)));
        try {
            String message = "Check out our new product:\n\n"
                    + "Label: " + produit.getNom() + "\n"
                    + "Price: " + produit.getPrix() + "dt\n"
                    + "Description: " + produit.getDescription() + "\n\n"
                    + "For more information, visit our Facebook page at https://www.facebook.com/profile.php?id=100055589231901";

            Image qrCodeImage = QrCodeGng.generateQRCodeImage(message, 200, 200);            imageViewQR.setImage(qrCodeImage);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void logout(ActionEvent event) {
        try {
            File sessionFile = new File("session.txt");
            if (sessionFile.exists()) {
                sessionFile.delete();
                System.out.println("Logged out successfully.");
                try{
                    Stage stage = (Stage) logoutbtn.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/templates/gui/LoginK.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/gui/LoginK.fxml"));
                    Parent loginRoot = loader.load();
                    double loginWidth = loginRoot.getScene().getWidth();
                    double loginHeight = loginRoot.getScene().getHeight();

                    stage.setWidth(loginWidth);
                    stage.setHeight(loginHeight);
                    stage.setResizable(true);
                    stage.show();

                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Session file does not exist.");
            }
        } catch (Exception ex) {
            System.out.println("Error logging out: " + ex.getMessage());
        }
    }



}
