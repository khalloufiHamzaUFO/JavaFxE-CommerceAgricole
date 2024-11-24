package templates.compltabilite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import models.Comptabilite;
import models.Facture;
import services.ComptabiliteCRUD;
import services.FactureCRUD;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CalculComtController implements Initializable {
    @FXML
    private Button btnSubmitt;

    @FXML
    private ChoiceBox<Date> cbCompt;

    @FXML
    private Label idComptt;

    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();
    FactureCRUD factureCRUD = new FactureCRUD();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Comptabilite> list = comptabiliteCRUD.getAll();
        ObservableList<Date> datesList = FXCollections.observableArrayList(
                list.stream().map(Comptabilite::getDate_comptabilite).collect(Collectors.toList()));

        cbCompt.setItems(datesList);
    }

    public void handleSubmit() {
        Date date = cbCompt.getValue();
        System.out.println(date);
        List<Facture> factures = factureCRUD.afficherFacture();
        float vente = 0;
        float achat = 0;
        for (Facture facture : factures) {
            System.out.println("...");
            System.out.println(facture.getDate());
            System.out.println(facture.getType());
            if (facture.getDate().equals(date)) {
                if (facture.getType().equalsIgnoreCase("achat")) {
                    System.out.println("found");
                    achat += facture.getMontant_total();
                } else if (facture.getType().equalsIgnoreCase("vente")) {
                    System.out.println("found");
                    vente += facture.getMontant_total();
                }
            }
        }
        float sum = vente - achat;
        Comptabilite c = comptabiliteCRUD.getByDate(date);
        c.setValeur(sum);
        System.out.println(sum);
        comptabiliteCRUD.modifier(c);
        idComptt.setText("Valeur final du comptabilite apres calcul = "+sum);
    }




}
