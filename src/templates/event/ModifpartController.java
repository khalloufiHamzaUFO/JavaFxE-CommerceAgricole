package templates.event;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Participation;
import services.PartService;

public class ModifpartController implements Initializable {

    @FXML
    private Button valbtn;
    @FXML
    private Button retbtn;
    @FXML
    private ImageView im1;
    @FXML
    private ImageView im2;
    @FXML
    private TextField tt11;
    @FXML
    private TextField tt22;
    @FXML
    private Label t21;
    @FXML
    private Label t31;
    @FXML
    private Label t4;
    private Participation participation;
    PartService ps = new PartService();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void initializeData(Participation part) {
        this.participation = part;
        tt11.setText(String.valueOf(part.getId_utilisateur_id()));
        tt22.setText(String.valueOf(part.getEvenement_id()));

    }
    public Participation getParticipation() {
        return participation;
    }


    public Participation setParticipation(Participation p) {
        return participation;
    }


    @FXML
    private void valider(ActionEvent event) {
        String Id_utilisateur_id = tt11.getText();
        String Evenement_id = tt22.getText();

        if (Id_utilisateur_id.isEmpty() || Evenement_id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis");
            alert.showAndWait();
            return;
        }

        participation.setId_utilisateur_id(Integer.parseInt(Id_utilisateur_id));
        participation.setEvenement_id(Integer.parseInt(Evenement_id));

        ps.modifier(participation);
    }

    @FXML
    private void retour(ActionEvent event) {
        event = null;
        ((Stage) tt11.getScene().getWindow()).close();
    }


}