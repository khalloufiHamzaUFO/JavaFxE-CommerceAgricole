package templates.facture;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Comptabilite;
import models.Culture;
import models.Facture;
import services.ComptabiliteCRUD;
import services.FactureCRUD;
import templates.compltabilite.ModComptContoller;
import templates.culture.ModifierCultureController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherFactures implements Initializable {

    @FXML
    private TableColumn<Facture, Float> ColValeur;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnMod;

    @FXML
    private TableColumn<Facture, String> colComtablite;

    @FXML
    private TableColumn<Facture, Date> colDate;

    @FXML
    private TableColumn<Facture, String> colType;

    @FXML
    private TableView<Facture> tvFact;
    FactureCRUD factureCRUD = new FactureCRUD();
    ComptabiliteCRUD comptabiliteCRUD = new ComptabiliteCRUD();
    ObservableList<Facture> factures = factureCRUD.afficherFacture();

    @FXML
    private TextField tfSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
        System.out.println(factures);
    }


    public void update() {
        ObservableList<Facture> factures = factureCRUD.afficherFacture();
        colComtablite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComptabilite().getDate_comptabilite().toString()));
        colDate.setCellValueFactory(cellData -> {
            SimpleObjectProperty<Date> property = new SimpleObjectProperty<>();
            java.util.Date localDate = cellData.getValue().getDate();
            Date date = Date.valueOf(String.valueOf(localDate));
            property.setValue(date);
            return property;
        });
        ColValeur.setCellValueFactory(new PropertyValueFactory<Facture, Float>("montant_total"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvFact.setItems(factures);
    }

    @FXML
    public void handleSearch() {
        String keyword = tfSearch.getText().toLowerCase();
        ObservableList<Facture> filteredFactures = factures.filtered(facture ->
                facture.getType().toLowerCase().contains(keyword) ||
                        facture.getComptabilite().getDate_comptabilite().toString().toLowerCase().contains(keyword) ||
                        facture.getDate().toString().toLowerCase().contains(keyword)
        );
        tvFact.setItems(filteredFactures);
    }

    @FXML
    private void Imprimer(ActionEvent event) throws FileNotFoundException, DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream("liste_Factures.pdf"));
        document.open();
        document.addTitle("Liste des Factures");
        PdfPTable table = new PdfPTable(4); // 3 colonnes
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);//Crée une police de caractères pour les en-têtes de colonnes
        PdfPCell cell = new PdfPCell(new Phrase("date", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("type", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("comptabilite", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("montant", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        for (Facture f : tvFact.getItems()) {
            table.addCell(f.getDate().toString());
            table.addCell(f.getType());
            table.addCell(String.valueOf(f.getComptabilite()));
            table.addCell(String.valueOf(f.getMontant_total())+ " Dt");

        }
        document.add(table);
        document.close();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window primaryStage = null;
            job.showPrintDialog(primaryStage);
            Node root = this.tvFact;
            job.printPage(root);
            job.endJob();
        }
    }

    @FXML
    public void handleDelete() {
        Facture selectedFacture = tvFact.getSelectionModel().getSelectedItem();
        if (selectedFacture != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Facture");
            alert.setContentText("Are you sure you want to delete this facture?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                factureCRUD.supprimer(selectedFacture.getId());
                update();
            }
        } else {
            // No facture is selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Facture Selected");
            alert.setContentText("Please select a facture to delete.");
            alert.showAndWait();
        }
    }
    public void relaod(){
        update();
    }



    @FXML
    public void handleEdit() {
        Facture selectedItem = tvFact.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModif.fxml"));
                Parent root = loader.load();

                ModifyFacture edit = loader.getController();
                edit.initData(selectedItem);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Facture");
                stage.showAndWait();
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
