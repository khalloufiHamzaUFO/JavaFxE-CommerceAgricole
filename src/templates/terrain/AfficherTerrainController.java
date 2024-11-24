
package templates.terrain;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Culture;
import models.Terrain;
import services.TerrainCRUD;

import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AfficherTerrainController implements Initializable {

    @FXML
    private TableView<Terrain> table_vieww;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField tfrecherche;
    @FXML
    private ComboBox<String> cbRecherche;
    ObservableList<String> listeTypeRecherche = FXCollections.observableArrayList("Tout", "numero", "surface", "lieu");

    @FXML
    private Button btnimpression;
    @FXML
    private ComboBox<String> cbtri;
    ObservableList<String> listeTypetri = FXCollections.observableArrayList("Tout", "culture", "numero", "surface", "lieu");
    ObservableList<Terrain> reslist = FXCollections.observableArrayList();
    @FXML
    private Button btnAfficher;
    @FXML
    private TableColumn<Terrain, String> Col_culture_id;
    @FXML
    private TableColumn<Terrain, Integer> Col_numero;
    @FXML
    private TableColumn<Terrain, Integer> Col_surface;
    @FXML
    private TableColumn<Terrain, String> Col_lieu;
    @FXML
    private Button btnmap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherTerrain();
        cbRecherche.setItems(listeTypeRecherche);
        cbRecherche.setValue("Tout");
        cbtri.setItems(listeTypetri);
        cbtri.setValue("Tout");
        try {
            listtri();
            try {
                list();
            } catch (SQLException ex) {
                Logger.getLogger(AfficherTerrainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AfficherTerrainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void AddTerrain(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("AjouterTerrain.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void ButtonAction(ActionEvent event) {
    }

    @FXML
    private void list() throws SQLException {
        TerrainCRUD terrainCRUD = new TerrainCRUD();
        List<Terrain> terrains = terrainCRUD.afficher();

        String recherche = cbRecherche.getValue();

        Predicate<Terrain> predicate = t -> true;
        if (!recherche.equals("Tout")) {
            switch (recherche) {
                case "lieu":
                    predicate = t -> t.getLieu().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                    break;
                case "culture":
                    try {
                        predicate = t -> {
                            Culture culture = t.getCulture();
                            return culture != null && culture.getType().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                        };
                    } catch (NullPointerException e) {
                        predicate = t -> false;
                    }
                    break;
                case "numero":
                    try {
                        int numero = Integer.parseInt(tfrecherche.getText());
                        predicate = t -> t.getNumero() == numero;
                    } catch (NumberFormatException e) {
                        predicate = t -> false;
                    }
                    break;
                case "surface":
                    try {
                        int surface = Integer.parseInt(tfrecherche.getText());
                        predicate = t -> t.getSurface() == surface;
                    } catch (NumberFormatException e) {
                        predicate = t -> false;
                    }
                    break;
                default:
                    break;
            }
        }

        List<Terrain> terrainsFiltres = terrains.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        reslist = FXCollections.observableArrayList(terrainsFiltres);
        table_vieww.setItems(reslist);
    }

    @FXML
    private void Imprimer1(ActionEvent event) throws FileNotFoundException, DocumentException {
        System.out.println("To Printer!");

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream("liste_terrains.pdf"));
        document.open();

        TableView<Terrain> table = this.table_vieww;
        ObservableList<TableColumn<Terrain, ?>> columns = table.getColumns();
        int columnCount = columns.size();
        PdfPTable pdfTable = new PdfPTable(columnCount);

        for (TableColumn<Terrain, ?> column : columns) {
            pdfTable.addCell(column.getText());
        }

        for (Terrain terrain : table.getItems()) {
            for (int i = 0; i < columnCount; i++) {
                TableColumn<Terrain, ?> column = columns.get(i);
                Object cellValue = column.getCellData(terrain);
                pdfTable.addCell(cellValue == null ? "" : cellValue.toString());
            }
        }

        document.add(pdfTable);

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
        PdfWriter.getInstance(document, new FileOutputStream("liste_terrains.pdf"));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Liste des terrains", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        TableView<Terrain> table = this.table_vieww;
        ObservableList<TableColumn<Terrain, ?>> columns = table.getColumns();
        int columnCount = columns.size();
        PdfPTable pdfTable = new PdfPTable(columnCount);

        for (TableColumn<Terrain, ?> column : columns) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.CYAN);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(column.getText()));
            pdfTable.addCell(header);
        }

        for (Terrain terrain : table.getItems()) {
            for (int i = 0; i < columnCount; i++) {
                TableColumn<Terrain, ?> column = columns.get(i);
                Object cellValue = column.getCellData(terrain);
                PdfPCell cell = new PdfPCell();
                cell.setPhrase(new Phrase(cellValue == null ? "" : cellValue.toString()));
                pdfTable.addCell(cell);
            }
        }

        document.add(pdfTable);

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
        TerrainCRUD tc = new TerrainCRUD();
        List<Terrain> terrains = tc.afficher();
        // Récupérer la valeur de recherche et de tri sélectionnée
        String tri = cbtri.getValue();
        // Créer un stream à partir de la liste des terrains
        Stream<Terrain> stream = terrains.stream();
        // Trier les terrains en fonction du tri sélectionné
        if (!tri.equals("Tout")) {
            Comparator<Terrain> comparator = null;
            switch (tri) {
                case "lieu":
                    comparator = Comparator.comparing(c -> c.getLieu().toLowerCase());
                    //comparator = Comparator.comparing(Terrain::getLieu);
                    break;
                case "Culture":
                    stream = stream.sorted((t1, t2) -> t1.getCulture().getType().compareTo(t2.getCulture().getType()));

                    break;
                case "Surface":
                    comparator = Comparator.comparing(Terrain::getSurface);
                    break;


                case "numero":
                    comparator = Comparator.comparing(Terrain::getNumero);
                    break;
                default:
                    comparator = null;
                    break;
            }


            if (comparator != null) {
                stream = stream.sorted(comparator);
            }

        }

        // Convertir le stream en liste et l'afficher dans la table view
        reslist = FXCollections.observableArrayList(stream.collect(Collectors.toList()));
        table_vieww.setItems(reslist);


    }


    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("../culture/AfficherCulturee.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void SupprimerTerrain(ActionEvent event) {
        try {
            // Récupérer le terrain à supprimer
            Terrain terrainASupprimer = table_vieww.getSelectionModel().getSelectedItem();

            // Vérifier si un terrain est bien sélectionnée
            if (terrainASupprimer == null) {
                showError("Veuillez sélectionner un terrain à supprimer");
                return;
            }

            // Supprimer le terrain de la base de données
            TerrainCRUD tc = new TerrainCRUD();
            tc.supprimer(terrainASupprimer);

            // Afficher un message de succès
            showSuccess("Le terrain a été supprimée avec succès");
            JOptionPane.showMessageDialog(null, "terrain supprimeé");
            TerrainData();
            table_vieww.refresh();

        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la suppression du terrain");
            e.printStackTrace();
        }
    }

    private void TerrainData() throws SQLException {
        ObservableList<Terrain> listTerrain = FXCollections.observableArrayList();
        Col_culture_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCulture().getType()));
        Col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        Col_surface.setCellValueFactory(new PropertyValueFactory<>("surface"));
        Col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        TerrainCRUD Tc = new TerrainCRUD();
        List<Terrain> terrains = Tc.afficher();
        listTerrain.addAll();
        table_vieww.setItems(listTerrain);
    }

    @FXML
    private void ModifierTerrain(ActionEvent event) throws IOException {
        // Récupération de la culture sélectionnée dans le tableau
        Terrain terrainAModifiee = table_vieww.getSelectionModel().getSelectedItem();
        // Vérifier si une culture est bien sélectionnée
        if (terrainAModifiee == null) {
            showError("Veuillez sélectionner un terrain à Modifier");
            return;
        }


        // Ouverture de la fenêtre de modification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierTerrain.fxml"));
        Parent root = loader.load();
        ModifierTerrainController mtc = loader.getController();

        // Transmission des données de la culture sélectionnée à la fenêtre de modification
        mtc.initData(terrainAModifiee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }


    @FXML
    private void AfficherTerrain() {
        TerrainCRUD tc = new TerrainCRUD();
        List<Terrain> terrains = tc.afficher();
        table_vieww.getItems().clear();
        for (Terrain terrain : terrains) {
        }

        table_vieww.getItems().addAll(terrains);
        Col_culture_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCulture().getType()));

        Col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        Col_surface.setCellValueFactory(new PropertyValueFactory<>("surface"));
        Col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
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

    @FXML
    private void Map(ActionEvent event) throws IOException {
        Parent rootRec3 = FXMLLoader.load(getClass().getResource("../equipement/testMap.fxml"));
        System.out.println("ss");
        Scene rec3 = new Scene(rootRec3);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec3);
        app.show();
    }
}

