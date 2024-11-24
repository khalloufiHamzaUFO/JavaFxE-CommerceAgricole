/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates.culture;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.JOptionPane;

import com.itextpdf.text.pdf.PdfPTable;
import models.Culture;
import services.CultureCRUD;

public class AfficherCultureeController implements Initializable {

    @FXML
    private TableColumn<Culture, String> Col_type;
    @FXML
    private TableColumn<Culture, Date> Col_date_planting;
    @FXML
    private TableColumn<Culture, Float> Col_quantite;
    @FXML
    TableView<Culture> table_vieww;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField tfType;
    @FXML
    private DatePicker dtDate_planting;
    @FXML
    private TextField tfQuantite;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField tfrecherche;
    @FXML
    private ComboBox<String> cbRecherche;
    ObservableList<String> listeTypeRecherche = FXCollections.observableArrayList("Tout", "type", "quantite");

    @FXML
    private Button btnimpression;
    @FXML
    private ComboBox<String> cbtri;
    ObservableList<String> listeTypetri = FXCollections.observableArrayList("Tout", "type", "date_planting", "quantite");
    ObservableList<Culture> reslist = FXCollections.observableArrayList();
    @FXML
    private Button btnAfficher;

    private Culture cultureAModifiee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbRecherche.setItems(listeTypeRecherche);
        cbRecherche.setValue("Tout");
        cbtri.setItems(listeTypetri);
        cbtri.setValue("Tout");
        try {
            listtri();
            try {
                list();
            } catch (SQLException ex) {
                Logger.getLogger(AfficherCultureeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AfficherCultureeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table_vieww.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {

            }
        });

        AfficherCulture();

    }



    @FXML
    private void SupprimerCulture(ActionEvent event) {
        try {
            Culture cultureASupprimer = table_vieww.getSelectionModel().getSelectedItem();
            if (cultureASupprimer == null) {
                showError("Veuillez sélectionner une culture à supprimer");
                return;
            }
            CultureCRUD cc = new CultureCRUD();
            cc.supprimer(cultureASupprimer);
            System.out.println(cultureASupprimer.getId());
            showSuccess("La culture a été supprimée avec succès");
            CultureData();
            table_vieww.refresh();
        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la suppression de la culture");
            e.printStackTrace();
        }
    }


    @FXML
    private void ModifierCulture(ActionEvent event) throws IOException {
        Culture cultureAModifiee = table_vieww.getSelectionModel().getSelectedItem();
        if (cultureAModifiee == null) {
            showError("Veuillez sélectionner une culture à Modifier");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierCulture.fxml"));
        Parent root = loader.load();
        ModifierCultureController mcc = loader.getController();

        mcc.initData(cultureAModifiee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ButtonAction(ActionEvent event) {

    }

    @FXML
    private void list() throws SQLException {
        CultureCRUD cultureCRUD = new CultureCRUD();
        List<Culture> cultures = cultureCRUD.afficher();

        String recherche = cbRecherche.getValue();

        Stream<Culture> stream = cultures.stream();

        if (!recherche.equals("Tout")) {
            stream = stream.filter(c -> {
                switch (recherche) {
                    case "type":
                        return c.getType().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                    case "quantite":
                        try {
                            float quantite = Float.parseFloat(tfrecherche.getText());
                            return c.getQuantite() == quantite;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    default:
                        return true;
                }
            });
        }
        reslist = FXCollections.observableArrayList(stream.collect(Collectors.toList()));
        table_vieww.setItems(reslist);
    }


    @FXML
    private void Imprimer1(ActionEvent event) throws FileNotFoundException, DocumentException {
        System.out.println("To Printer!");
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream("liste_cultures.pdf"));
        document.open();
        document.addTitle("Liste des cultures");
        PdfPTable table = new PdfPTable(3); // 3 colonnes
        table.addCell("Type");
        table.addCell("Date de plantation");
        table.addCell("Quantité");
        for (Culture culture : table_vieww.getItems()) {
            table.addCell(culture.getType());
            table.addCell(culture.getDate_planting().toString());
            table.addCell(String.valueOf(culture.getQuantite()));
        }
        document.add(table);
        document.close();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window primaryStage = null;
            job.showPrintDialog(primaryStage);
            Node root = this.table_vieww;
            job.printPage(root);
            job.endJob();
        }
    }

    @FXML
    private void Imprimer(ActionEvent event) throws FileNotFoundException, DocumentException {
        System.out.println("To Printer!");
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream("liste_cultures.pdf"));
        document.open();
        document.addTitle("Liste des cultures");
        PdfPTable table = new PdfPTable(3); // 3 colonnes
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);//Crée une police de caractères pour les en-têtes de colonnes
        PdfPCell cell = new PdfPCell(new Phrase("Type", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Date de plantation", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quantité", titleFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(cell);

        for (Culture culture : table_vieww.getItems()) {
            table.addCell(culture.getType());
            table.addCell(culture.getDate_planting().toString());
            table.addCell(String.valueOf(culture.getQuantite()));
        }
        document.add(table);
        document.close();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window primaryStage = null;
            job.showPrintDialog(primaryStage);
            Node root = this.table_vieww;
            job.printPage(root);
            job.endJob();
        }
    }


    @FXML
    private void listtri() throws SQLException {
        CultureCRUD cc = new CultureCRUD();
        List<Culture> cultures = cc.afficher();
        String tri = cbtri.getValue();
        Stream<Culture> stream = cultures.stream();
        if (!tri.equals("Tout")) {
            Comparator<Culture> comparator;
            switch (tri) {
                case "type":
                    comparator = Comparator.comparing(c -> c.getType().toLowerCase());
                    break;
                case "date_planting":
                    comparator = Comparator.comparing(Culture::getDate_planting);
                    break;
                case "quantite":
                    comparator = Comparator.comparing(Culture::getQuantite);
                    break;
                default:
                    comparator = null;
                    break;
            }

            if (comparator != null) {
                stream = stream.sorted(comparator);
            }
        }
        reslist = FXCollections.observableArrayList(stream.collect(Collectors.toList()));
        table_vieww.setItems(reslist);
    }


    @FXML
    private void AfficherCulture() {
        CultureCRUD cc = new CultureCRUD();
        List<Culture> cultures = cc.afficher();
        table_vieww.getItems().clear();
        table_vieww.getItems().addAll(cultures);
        Col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        Col_date_planting.setCellValueFactory(new PropertyValueFactory<>("date_planting"));
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("../terrain/AfficherTerrain.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private void CultureData() throws SQLException {
        ObservableList<Culture> listCulture = FXCollections.observableArrayList();

        Col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Col_date_planting.setCellValueFactory(new PropertyValueFactory<>("date_planting"));
        Col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        CultureCRUD cc = new CultureCRUD();
        List<Culture> cultures = cc.afficher();
        listCulture.addAll();
        table_vieww.setItems(listCulture);
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
}
