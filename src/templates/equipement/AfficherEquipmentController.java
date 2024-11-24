package templates.equipement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import javax.swing.JOptionPane;

import models.Equipement;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.EquipementCRUD;

public class AfficherEquipmentController implements Initializable {


    private TableColumn<Equipement, Integer> col_id;
    @FXML
    private TableColumn<Equipement, String> col_employe_id;
    @FXML
    private TableColumn<Equipement, String> col_Type;
    @FXML
    private TableColumn<Equipement, String> col_Marque;
    @FXML
    private TableColumn<Equipement, String> col_etat;
    @FXML
    private TableColumn<Equipement, Integer> col_disponnible;
    @FXML
    private TableColumn<Equipement, String> col_matricule;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;

    @FXML
    private TextField tfrecherche;
    @FXML
    private ComboBox<String> cbRecherche;
    ObservableList<String> listeTypeRecherche = FXCollections.observableArrayList("Tout", "Type", "Marque", "etat", "matricule");
    @FXML
    private ComboBox<String> cbtri;
    ObservableList<String> listeTypetri = FXCollections.observableArrayList("Tout", "Type", "Marque", "etat", "matricule", "employe", "disponnible");
    ObservableList<Equipement> reslist = FXCollections.observableArrayList();
    @FXML
    private Button btnAfficher;
    @FXML
    private TableView<Equipement> table_vieww;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfpassword;
    @FXML
    private TextField tffirstname;
    @FXML
    private TextField tflastname;
    @FXML
    private TextField tfNbrTel;
    @FXML
    private Button btnstat;
    @FXML
    private Button btngenerateExcel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherEquipment();
        cbRecherche.setItems(listeTypeRecherche);
        cbRecherche.setValue("Tout");
        cbtri.setItems(listeTypetri);
        cbtri.setValue("Tout");
        try {
            listtri();
            list();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherEquipmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void list() {
        EquipementCRUD pcd = new EquipementCRUD();
        List<Equipement> Equipements = pcd.getAll();
        String recherche = cbRecherche.getValue();
        Stream<Equipement> stream = Equipements.stream();
        if (!recherche.equals("Tout")) {
            stream = stream.filter(c -> {
                switch (recherche) {
                    case "type":
                        return c.getType().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                    case "marque":
                        return c.getMarque().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                    case "etat":
                        return c.getEtat().toLowerCase().contains(tfrecherche.getText().toLowerCase());
                    case "matricule":
                        return c.getMatricule().toLowerCase().contains(tfrecherche.getText().toLowerCase());

                    default:
                        return true;
                }
            });
        }
    }

    @FXML
    private void listtri() throws SQLException {
        EquipementCRUD pcd = new EquipementCRUD();
        List<Equipement> Equipements = pcd.getAll();
        String tri = cbtri.getValue();
        Stream<Equipement> stream = Equipements.stream();
        if (!tri.equals("Tout")) {
            Comparator<Equipement> comparator;
            switch (tri) {
                case "type":
                    comparator = Comparator.comparing(c -> c.getType().toLowerCase());
                    break;

                case "marque":
                    comparator = Comparator.comparing(c -> c.getMarque().toLowerCase());
                    break;
                case "etat":
                    comparator = Comparator.comparing(c -> c.getEtat().toLowerCase());
                    break;
                case "disponnible":
                    comparator = Comparator.comparingInt(Equipement::isDisponnible);
                    break;
                case "matricule":
                    comparator = Comparator.comparing(c -> c.getMatricule().toLowerCase());
                    break;
                case "employe":
                    stream = stream.sorted((t1, t2) -> t1.getEmploye_().getCin().compareTo(t2.getEmploye_().getCin()));

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
    private void retour(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("../employe/AfficherEmploye.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
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
    private void AddEquipment(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("AjouterEquipment.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void SupprimerEquipment(ActionEvent event) {
        try {
            Equipement EquipmentASupprimer = table_vieww.getSelectionModel().getSelectedItem();
            if (EquipmentASupprimer == null) {
                showError("Veuillez sélectionner un Equipement à supprimer");
                return;
            }
            EquipementCRUD EquipementCRUD = new EquipementCRUD();
            EquipementCRUD.supprimer(EquipmentASupprimer);
            showSuccess("L'Equipement a été supprimée avec succès");
            JOptionPane.showMessageDialog(null, "Equipement supprimeé");
            EquipmentData();
            table_vieww.refresh();

        } catch (Exception e) {
            showError("Une erreur s'est produite lors de la suppression de l'Equipement");
            e.printStackTrace();
        }

    }

    private void EquipmentData() throws SQLException {
        ObservableList<Equipement> listEquipement = FXCollections.observableArrayList();
        col_employe_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmploye_().getCin()));

        col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_Marque.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_disponnible.setCellValueFactory(new PropertyValueFactory<>("disponnible"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));

        EquipementCRUD ec = new EquipementCRUD();
        List<Equipement> Equipements = ec.getAll();
        listEquipement.addAll();
        table_vieww.setItems(listEquipement);
    }

    @FXML
    private void ModifierEquipment(ActionEvent event) throws IOException {
        Equipement equipementAModifiee = table_vieww.getSelectionModel().getSelectedItem();
        if (equipementAModifiee == null) {
            showError("Veuillez sélectionner un Equipement à Modifier");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEquipement.fxml"));
        Parent root = loader.load();
        ModifierEquipementController mtc = loader.getController();
        mtc.initData(equipementAModifiee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private void AfficherEquipment() {
        EquipementCRUD ecd = new EquipementCRUD();
        List<Equipement> Equipements = ecd.getAll();
        table_vieww.getItems().clear();
        table_vieww.getItems().addAll(Equipements);
        col_employe_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmploye_().getCin()));

        col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_Marque.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_disponnible.setCellValueFactory(new PropertyValueFactory<>("disponnible"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("stats.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void generateExcel(ActionEvent event) {
        EquipementCRUD pcd = new EquipementCRUD();
        List<Equipement> Equipements = pcd.getAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Equipement");
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("employe");
        headerRow.createCell(1).setCellValue("Type");
        headerRow.createCell(2).setCellValue("Marque");
        headerRow.createCell(3).setCellValue("disponnible");
        headerRow.createCell(4).setCellValue("etat");
        headerRow.createCell(5).setCellValue("matricule");
        for (int i = 0; i < Equipements.size(); i++) {
            Equipement equipement = Equipements.get(i);

            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(equipement.getEmploye_().getCin());
            row.createCell(1).setCellValue(equipement.getType());
            row.createCell(2).setCellValue(equipement.getMarque());
            row.createCell(3).setCellValue(equipement.isDisponnible());
            row.createCell(4).setCellValue(equipement.getEtat());
            row.createCell(5).setCellValue(equipement.getMatricule());
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("equipements.xlsx");
            workbook.write(outputStream);
            workbook.close();
            System.out.println("****");
            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Excel");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier a été exporté avec succès !");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Excel");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'export du fichier !");
            alert.showAndWait();
        }
    }

}
