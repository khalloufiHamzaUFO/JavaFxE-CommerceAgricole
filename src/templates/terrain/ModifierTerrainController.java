package templates.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.stage.Stage;
import models.Culture;
import models.Terrain;
import services.CultureCRUD;
import services.TerrainCRUD;
import templates.terrain.AfficherTerrainController;
import templates.terrain.AjouterTerrainController;

import javax.imageio.ImageIO;

public class ModifierTerrainController implements Initializable {

    @FXML
    private TextField tfSurface;
    @FXML
    private TextField tfLieu;
    @FXML
    private ComboBox<Culture> cbCulture;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField tfNumero;
    @FXML
    private Button btnimage;
    @FXML
    private ImageView ivImage;
    @FXML
    private ImageView Im_agro;

    private Image image;
    private FileChooser fileChooser;
    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Culture> cultures = new CultureCRUD().afficher();
        cbCulture.setItems(FXCollections.observableArrayList(cultures));
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers d'image", "*.png", "*.jpg")
        );
    }

    private Terrain terrainAModifier;

    public void initData(Terrain terrain) {
        terrainAModifier = terrain;

        tfNumero.setText(String.valueOf(terrain.getNumero()));
        tfSurface.setText(String.valueOf(terrain.getSurface()));
        tfLieu.setText(terrain.getLieu());
        cbCulture.setValue(terrain.getCulture());
        String imagePath = terrain.getImage().replaceAll("^file:/+", "");

        if (imagePath != null) {
            try {
                ivImage.setImage(new Image(new File(imagePath).toURI().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ivImage.setImage(null);
        }
    }

    @FXML
    private void ModifierTerrain(ActionEvent event) {
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
                showError("Veuillez sélectionner un terrain.");
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

            String filename = selectedFile.getName();
            String extension = filename.substring(filename.lastIndexOf(".") + 1);

            if (!(extension.equals("png") || extension.equals("jpg"))) {
                showError("Veuillez sélectionner un fichier image PNG ou JPG.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherTerrain.fxml"));
            Parent root = loader.load();
            AfficherTerrainController afficherTerrainController = loader.getController();
            TerrainCRUD tc = new TerrainCRUD();
            terrainAModifier.setCulture(culture); // Mettre à jour la culture sélectionnée
            terrainAModifier.setImage(selectedFile.toURI().toString());
            terrainAModifier.setLieu(lieu);
            terrainAModifier.setNumero(Integer.parseInt(numero));
            terrainAModifier.setSurface(Integer.parseInt(surface));
            tc.modifier(terrainAModifier);
            showSuccess("La terrain a été modifiée avec succès");

            cbCulture.setValue(null);
            tfNumero.setText("");
            tfSurface.setText("");
            tfLieu.setText("");
            ivImage.setImage(null);

            Stage stage = (Stage) btnModifier.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la modification de la terrain");
            e.printStackTrace();
        }
    }


    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
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
        Parent root = FXMLLoader.load(getClass().getResource("AfficherTerrain.fxml"));
        ;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

}
