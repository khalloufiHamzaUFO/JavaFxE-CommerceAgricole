package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Categorie;
import services.ServiceCategorie;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

public class CategorieFormController implements Initializable {

    @FXML
    private Button btnIcon;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnSubmitModif;

    @FXML
    private TextField tfIcon;

    @FXML
    private TextField tfLabel;


    @FXML
    private ImageView imageView;
    ServiceCategorie serviceCategorie = new ServiceCategorie();
    private Categorie selectedCategorie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleButtonSubmit() throws IOException {
        String imagePath = tfIcon.getText();
        if (!tfLabel.getText().isEmpty() && !tfIcon.getText().isEmpty()) {
            boolean categoryExists = false;
            for (Categorie c : serviceCategorie.getAll()) {
                if (c.getLabel().equals(tfLabel.getText())) {
                    categoryExists = true;
                    break;
                }
            }
            String[] allowedExtensions = {"jpg", "jpeg", "png"};
            if (!isValidFileExtension(imagePath, allowedExtensions)) {
                error( "Fichier image invalide");
                return;
            }
            if (categoryExists) {
                error( "Une catégorie avec ce label existe déjà.");
            } else {
                String label = tfLabel.getText();
                String icon = tfIcon.getText();
                Categorie c = new Categorie(label, icon);
                serviceCategorie.ajouter(c);
                tfLabel.setText("");
                infomat( "Catégorie ajoutée avec succès !");
            }
        } else {
            error( "Champ obligatoire");
        }

    }


    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnIcon.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
                tfIcon.setText(fileName);
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isValidFileExtension(String filePath, String[] allowedExtensions) {
        String extension = "";

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }

        for (String allowedExtension : allowedExtensions) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void handleBtnRetour(MouseEvent event) throws IOException {
        AnchorPane formProduit = new AnchorPane();
        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("../templates/categorie/consulterCategorie.fxml")));
        Scene currentScene = btnRetour.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }

    public void handlebtnModif() throws IOException {
        if (!(tfLabel.getText().isEmpty()) || !(tfIcon.getText().isEmpty())) {
            boolean categoryExists = false;
            String label = tfLabel.getText();
            String icon = tfIcon.getText();

            ServiceCategorie serviceCategorie = new ServiceCategorie();
            List<Categorie> categories = serviceCategorie.getAll();
            categories.removeIf(p -> p.getId() == selectedCategorie.getId());
            for (Categorie c : categories) {
                if (c != selectedCategorie && c.getLabel().equals(label)) {
                   error("Une catégorie avec ce label existe déjà.");
                    return;
                }
            }
            if (!categoryExists) {
                Categorie updatedCategorie = new Categorie(selectedCategorie.getId(), label, icon);
                try {
                    serviceCategorie.modifier(updatedCategorie);
                    infomat("La catégorie a été mise à jour avec succès !");
                    ((Stage) btnSubmitModif.getScene().getWindow()).close();

                } catch (Exception e) {
                    error("Erreur lors de la mise à jour de la catégorie.");
                    e.printStackTrace();
                }
            }
        }

    }

    public void setSelectedCtegorie(Categorie categorie) {
        selectedCategorie = categorie;
        tfLabel.setText(selectedCategorie.getLabel());
        tfIcon.setText(selectedCategorie.getIcon());
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
