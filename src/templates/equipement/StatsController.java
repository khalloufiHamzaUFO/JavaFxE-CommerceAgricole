package templates.equipement;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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


public class StatsController implements Initializable {

    @FXML
    private PieChart PieChart;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         EquipementCRUD ec = new EquipementCRUD();
        List<Equipement> equipements = ec.getAll();

        Map<String, Long> counts = equipements.stream()
                .collect(Collectors.groupingBy(e -> e.getEtat().toString(), Collectors.counting()));

        int totalCount = equipements.size();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            String etat = entry.getKey();
            long count = entry.getValue();
            double percentage = count * 100.0 / equipements.size();
            pieChartData.add(new PieChart.Data(etat, percentage));
            System.out.println("le pourcentege est"+percentage);
             label.setText("le pourcentege est"+percentage);
        }

        PieChart.setAnimated(true);
       PieChart.setData(pieChartData);

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
