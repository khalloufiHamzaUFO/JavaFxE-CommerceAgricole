package templates.gui;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.user.Utilisateur;
import services.ServiceUser;
import utils.DataSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class GestionUtilisateurController implements Initializable {

    @FXML
    private TableView<Utilisateur> tableviewUtilisateur;
    @FXML
    private TableColumn<?, ?> nom1;
    @FXML
    private TableColumn<?, ?> prenom1;
    @FXML
    private TableColumn<?, ?> cin1;
    @FXML
    private TableColumn<?, ?> mail1;

    @FXML
    private TableColumn<?, ?> tel1;
    @FXML
    private Button modifierbtn;
    @FXML
    private Button ajouterbtn;
    @FXML
    private Button suppbtn;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField cin;
    @FXML
    private TextField tel;
    @FXML
    private TextField mail;
    @FXML
    private TextField password;

    @FXML
    private Button actualiser;


    Utilisateur user = null;

    ServiceUser su = new ServiceUser();

    private List<Utilisateur> l;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Button logoutbtn;
    @FXML
    private ComboBox<String> roless;
    @FXML
    private TableColumn<?, ?> roles1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showRec();
        roless.getItems().addAll("USER_ROLES", "ADMIN_ROLES");

    }


    @FXML
    public void showRec() //affiche une liste utilisateur fe table view
    {

        ObservableList<Utilisateur> list = getUtilisateurList();

        mail1.setCellValueFactory(new PropertyValueFactory<>("email"));
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cin1.setCellValueFactory(new PropertyValueFactory<>("cin"));
        tel1.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        roles1.setCellValueFactory(new PropertyValueFactory<>("roles"));
        tableviewUtilisateur.setItems(list);

    }

    public ObservableList<Utilisateur> getUtilisateursList() {
        cnx = DataSource.getInstance().getCnx();

        ObservableList<Utilisateur> List = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM  utilisateur ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            Utilisateur user;
            ResultSet rs = smt.executeQuery();
            while (rs.next()) { //parcour les enregistrement retoune par la requette sql
                user = new Utilisateur(rs.getString("nom"), rs.getString("prenom"), rs.getInt("id"), rs.getString("telephone"), rs.getString("email"), rs.getString("password"), rs.getString("roles"));
                List.add(user);//ajout utilisateur fe liste 
            }
            System.out.println(List);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return List;
    }

    @FXML
    private void modifierUtilisateur(ActionEvent event) throws SQLException, IOException {
        ServiceUser su = new ServiceUser();
        Utilisateur user = tableviewUtilisateur.getSelectionModel().getSelectedItem();

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur à modifier");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUtilisateur.fxml"));
        Parent root = loader.load();

        ModifierUtilisateurController controller = loader.getController();
        controller.initData(user);

        Stage stage = new Stage();
        stage.setTitle("Modifier l'utilisateur");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        Utilisateur utilisateurModifie = controller.getUtilisateur();

        if (utilisateurModifie != null) {
            su.modifier(utilisateurModifie);

            // Mettre à jour la table
            ObservableList<Utilisateur> userList = tableviewUtilisateur.getItems();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId() == utilisateurModifie.getId()) {
                    userList.set(i, utilisateurModifie);
                    break;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a été modifié avec succès");
            alert.showAndWait();
        }
    }

    @FXML
    private void ajouterUtilisateur(ActionEvent event) throws SQLException {
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || mail.getText().isEmpty() || cin.getText().isEmpty() || password.getText().isEmpty() || roless.getValue().isEmpty() || tel.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Champs invalides ! ", ButtonType.OK);
            a.showAndWait();

        } else if (password.getText().length() < 8) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe doit être >8 caractéres !)", ButtonType.OK);
            a.showAndWait();
        } else {


            Utilisateur u = new Utilisateur(nom.getText(), prenom.getText(), Integer.parseInt(cin.getText()), tel.getText(), mail.getText(), password.getText(), roless.getValue());
            su.ajouter(u);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Utilisateur ajouté(e) avec succes !", ButtonType.OK);
            a.showAndWait();


        }

    }

    private void refresh()
    {
        ObservableList<Utilisateur> list = getUtilisateurList();
        mail1.setCellValueFactory(new PropertyValueFactory<>("email"));
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cin1.setCellValueFactory(new PropertyValueFactory<>("cin"));
        tel1.setCellValueFactory(new PropertyValueFactory<>("tel"));
        String selectedRole = roless.getValue();


        tableviewUtilisateur.setItems(list);
    }


    @FXML
    private void supprimerUtilisateur(ActionEvent event) throws SQLException {

        ServiceUser su = new ServiceUser();
        Utilisateur user = (Utilisateur) tableviewUtilisateur.getSelectionModel().getSelectedItem();

        System.out.println("user :" + user.getNom());
        su.supprimerUtilisateur(user);
        refresh();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("AgroEasy :: Succes Message ");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();

    }

    private ObservableList<Utilisateur> getUtilisateurList() {
        cnx = DataSource.getInstance().getCnx();

        ObservableList<Utilisateur> List = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM  utilisateur ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            Utilisateur utilisateur;
            ResultSet rs = smt.executeQuery();
            while (rs.next()) { //parcour les enregistrement retoune par la requette sql
                utilisateur = new Utilisateur(rs.getString("nom"), rs.getString("prenom"), rs.getInt("cin"), rs.getString("telephone"), rs.getString("email"), rs.getString("password"), rs.getString("roles"));
                List.add(utilisateur);//ajout utilisateur fe liste 
            }
            System.out.println(List);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return List;
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            File sessionFile = new File("session.txt");
            if (sessionFile.exists()) {
                sessionFile.delete();
                System.out.println("Logged out successfully.");
                try {
                    Stage stage = (Stage) logoutbtn.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
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
