package templates.equipement;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Equipement;
import services.EquipementCRUD;


public class StatController implements Initializable {

    @FXML
    private PieChart PieChart;
ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
    @FXML
    private Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          EquipementCRUD ec = new EquipementCRUD();
        List<Equipement> equipements = ec.getAll();
        Map<String, Integer> counts = new HashMap<>();

        for (Equipement e : equipements) {
            String etat = e.getEtat().toString();
            counts.put(etat, counts.getOrDefault(etat, 0) + 1);
            
        }
int totalCount=equipements.size();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String etat = entry.getKey();
            int count = entry.getValue();
            double percentage = count * 100.0 / equipements.size();
            list.add(new PieChart.Data(etat, percentage));
           
        }

        PieChart.setAnimated(true);
        PieChart.setData(list);
        // afficher le nombre total d'équipements
        label.setText("Nombre total d'équipements : " + totalCount);
    }

    @FXML
    private void retour(ActionEvent event)   throws IOException {
        Stage primaryStage = null;
        Stage SecondWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEquipment.fxml"));;
        Scene rec2 = new Scene(root);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show(); 
    }



    
}
