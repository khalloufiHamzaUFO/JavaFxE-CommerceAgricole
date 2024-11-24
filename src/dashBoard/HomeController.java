package dashBoard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import animatefx.animation.BounceIn;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController implements Initializable {
    @FXML
    private Button btnCategorie;

    @FXML
    private Button btnProduit;

    @FXML
    private Button btnAddCategorie;

    @FXML
    private Button logoutbtn;

    @FXML
    private Button btnAddProduit;

    @FXML
    private AnchorPane holderPane;

    AnchorPane categories, produits,edit,welcome,compt,addCompt,Facts,addFacts,aadProduits,parts,events,addCategorie,users,equipements,addCultures,cultures,employes,addEmploye,addTerrain,terrains,addEquipement;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            produits = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/produit/consulterProduit.fxml")));
            aadProduits = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/produit/formProduit.fxml")));
            //categorie
            categories = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/categorie/consulterCategorie.fxml")));
            addCategorie = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/categorie/formCategorie.fxml")));
            //equipement
            equipements = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/equipement/AfficherEquipment.fxml")));
            addEquipement = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/equipement/AjouterEquipment.fxml")));
            //employe
            employes = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/employe/AfficherEmploye.fxml")));
            addEmploye = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/employe/AjouterEmploye.fxml")));
            //terrain
            terrains = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/terrain/AfficherTerrain.fxml")));
            addTerrain = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/terrain/AjouterTerrain.fxml")));
            //culture
            cultures = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/culture/AfficherCulturee.fxml")));
            addCultures = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/culture/AjouterCulture.fxml")));
            //Events
            events = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/event/AddEvent.fxml")));
            parts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/event/AddPart.fxml")));
            //compt
            compt = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/compltabilite/AfficherCompt.fxml")));
            addCompt = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/compltabilite/AddCompt.fxml")));
            //compt
            Facts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/facture/AfficherFactrues.fxml")));
            addFacts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/facture/FXMLAjout.fxml")));

            edit = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/gui/UpdateProfile.fxml")));
            users = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/gui/GestionUtilisateur.fxml")));

            welcome = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../templates/produit/consulterProduit.fxml")));

            setNode(produits);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setNode(Node node) {
        if (node != null) {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(node);
            BounceIn bounceIn = new BounceIn(node);
            bounceIn.play();
        }
    }


    @FXML
    private void switchAddCategories() {
        setNode(addCategorie);
    }

    @FXML
    private void switchEquipement() {
        setNode(equipements);
    }

    @FXML
    private void switchEmploye() {
        setNode(employes);
    }

    @FXML
    private void switchUsers() {
        setNode(users);
    }

    @FXML
    private void switchTerrain() {
        setNode(terrains);
    }

    @FXML
    private void switchAddTerrain() {
        setNode(addTerrain);
    }

    @FXML
    private void switchAddProduits() {
        setNode(aadProduits);
    }

    @FXML
    private void switchCategories() {
        setNode(categories);
    }

    @FXML
    private void switchProduits() {
        setNode(produits);
    }

    @FXML
    private void switchAddEmploye() {
        setNode(addEmploye);
    }

    @FXML
    private void switchAddEquipement() {
        setNode(addEquipement);
    }

    @FXML
    private void switchAddCultture() {
        setNode(addCultures);
    }

    @FXML
    private void switchCultures() {
        setNode(cultures);
    }

    @FXML
    private void switchEvents() {
        setNode(events);
    }

    @FXML
    private void switchPart() {
        setNode(parts);
    }

    @FXML
    private void switchCompt() {
        setNode(compt);
    }

    @FXML
    private void switchaddComt() {
        setNode(addCompt);
    }

    @FXML
    private void switchFacts() {
        setNode(Facts);
    }

    @FXML
    private void switchAddFacts() {
        setNode(addFacts);
    }

    @FXML
    private void switchEdit() {
        setNode(edit);
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
                    Parent root =FXMLLoader.load(getClass().getResource("/templates/gui/LoginK.fxml"));
                    Scene scene = new Scene(root );
                    stage.setScene(scene);
                    stage.setResizable(false);
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
//
//    @FXML
//    public void handleBtnRetour(MouseEvent event) throws IOException {
//        AnchorPane formProduit = new AnchorPane();
//        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("../templates/categorie/consulterCategorie.fxml")));
//        Scene currentScene = btnRetour.getScene();
//        Stage currentStage = (Stage) currentScene.getWindow();
//        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
//        currentStage.setScene(newScene);
//    }


}
