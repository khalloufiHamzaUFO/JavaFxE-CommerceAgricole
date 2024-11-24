package templates.event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Evenement;
import services.EvenementService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModifController implements Initializable {

    @FXML
    private Button btn9;
    @FXML
    private Button btn8;
    @FXML
    private ImageView im1;
    @FXML
    private ImageView im2;
    @FXML
    private ImageView imv;
    @FXML
    private TextField titre;
    @FXML
    private TextField lieu;
    @FXML
    private TextField image;
    @FXML
    private Label tx2;
    @FXML
    private Label tx3;
    @FXML
    private Label tx4;
    private Evenement evenement;
    EvenementService es = new EvenementService();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void initializeData(Evenement evenement) {
        // Afficher les informations de l'événement dans les champs de texte
        titre.setText(evenement.getTitre());
        lieu.setText(evenement.getLieu());
        image.setText(evenement.getImage());

        // Charger l'image de l'événement et l'afficher
        Image image = new Image(new File(evenement.getImage()).toURI().toString());
        imv.setImage(image);

        // Stocker l'événement en cours de modification
        this.evenement = evenement;
    }
    public Evenement getEvenement() {
        return evenement;
    }


    public Evenement setEvenement(Evenement e) {
        return evenement;
    }


    @FXML
    private void valider(ActionEvent event) {
        String Titre = titre.getText();
        String Lieu = lieu.getText();
        String Image = image.getText();

        if (Titre.isEmpty() || Lieu.isEmpty() || Image.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis");
            alert.showAndWait();
            return;
        }

        evenement.setTitre(Titre);
        evenement.setLieu(Lieu);
        evenement.setImage(Image);
        Image image110= new Image(new File(evenement.getImage()).toURI().toString());
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);
            FileWriter writer = new FileWriter("evenements.txt", true);
            writer.write(Stream.of(evenement.getId(), evenement.getTitre(), evenement.getLieu(), new File(evenement.getImage()).toURI().toString())
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) + System.lineSeparator());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        es.modifier(evenement);

        // ...
    }

    @FXML
    private void retour(ActionEvent event) {
        event = null;
        ((Stage) titre.getScene().getWindow()).close();
    }
    @FXML
    private void chooseImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            String imagePath = file.getAbsolutePath();
            image.setText(imagePath);
            Evenement evenement = new Evenement();
            evenement.setImage(imagePath);
            evenement.setImageFile(es.loadImage(imagePath));
            imv.setImage(evenement.getImageFile());
        }

    }}