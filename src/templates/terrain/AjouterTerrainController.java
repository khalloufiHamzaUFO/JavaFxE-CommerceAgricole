package templates.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Culture;
import models.Terrain;
import services.CultureCRUD;
import services.TerrainCRUD;

import javax.imageio.ImageIO;


public class AjouterTerrainController implements Initializable {

    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfSurface;
    @FXML
    private TextField tfLieu;
    @FXML
    private ComboBox<Culture> cbCulture;
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnimage;

    @FXML
    private ImageView ivImage;

    private Image image;
    private FileChooser fileChooser;
    private File imageFile;
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Culture> cultures = new CultureCRUD().afficher();
        cbCulture.setItems(FXCollections.observableArrayList(cultures));
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Fichiers d'image", "*.png", "*.jpg")
        );
    }

    @FXML
    private void AddTerrain(ActionEvent event) {
        try {
            String numero = tfNumero.getText();

            if (numero.isEmpty()) {
                showError("Veuillez saisir un numero");
                return;
            }
            float numerof;
            try {
                numerof = Float.parseFloat(numero);
            } catch (NumberFormatException e) {
                showError("Le numero doit être un nombre décimal");
                return;
            }
            String surface = tfSurface.getText();
            if (surface.isEmpty()) {
                showError("Veuillez saisir une surface");
                return;
            } else {
                try {
                    int surfaceInt = Integer.parseInt(surface);
                    if (surfaceInt <= 0) {
                        showError("La surface doit être un entier positif.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showError("La surface doit être un entier.");
                    return;
                }
            }
            String lieu = tfLieu.getText();

            if (lieu.isEmpty()) {
                showError("Veuillez saisir un lieu");
                return;
            } else if (lieu.length() <= 2 || lieu.length() >= 20) {
                showError("Le lieu du terrain doit contenir entre 2 et 20 caractères.");
                return;
            }

            Culture culture = cbCulture.getValue();
            if (culture == null) {
                showError("Veuillez sélectionner une culture.");
                return;
            }

            Image image = ivImage.getImage();
            if (image == null) {
                showError("Veuillez sélectionner une image.");
                return;
            }

            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                showError("Veuillez sélectionner un fichier image.");
                return;
            }

            String filename = selectedFile.toURI().toString();
            String extension = filename.substring(filename.lastIndexOf(".") + 1);

            if (!(extension.equals("png") || extension.equals("jpg"))) {
                showError("Veuillez sélectionner un fichier image PNG ou JPG.");
                return;
            }

            if (numero.isEmpty() || surface.isEmpty() || lieu.isEmpty() || culture == null || image == null) {
                showError("Veuillez remplir tous les champs");
                return;
            }


            Terrain t = new Terrain(culture, Integer.parseInt(numero), Integer.parseInt(surface), lieu, filename);

            TerrainCRUD tc = new TerrainCRUD();
            tc.ajouter2(t);
            showSuccess("Le terrain a été ajouté avec succès.");
            tfNumero.clear();
            tfSurface.clear();
            tfLieu.clear();
            cbCulture.getSelectionModel().clearSelection();
            ivImage.setImage(null);
        } catch (Exception ex) {
            Logger.getLogger(AjouterTerrainController.class.getName()).log(Level.SEVERE, null, ex);
            showError("Une erreur est survenue lors du chargement de l'image.");
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
        alert.setTitle("Le terrain a été ajouté avec succès.");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Tous les fichiers", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                ivImage.setImage(SwingFXUtils.toFXImage(image, null));

            } catch (IOException ex) {
                showError("Erreur lors du chargement de l'image.");
                Logger.getLogger(AjouterTerrainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherTerrain.fxml"));
        ;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }
}
