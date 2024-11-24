package services;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import models.Evenement;
import repositories.IService;
import utils.DataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;

public class EvenementService {
    private Connection connection;

    public EvenementService() {
        connection = DataSource.getInstance().getCnx();
    }

    public void ajouter(Evenement evenement) {
        try {
            String query = "INSERT INTO evenement (titre, date, lieu, image, updated_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, evenement.getTitre());
            preparedStatement.setDate(2, new Date(evenement.getDate().getTime()));
            preparedStatement.setString(3, evenement.getLieu());
            preparedStatement.setString(4, evenement.getImage());
            preparedStatement.setTimestamp(5, evenement.getUpdated_at());
            preparedStatement.executeUpdate();
            System.out.println("Événement ajouté avec succès!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifier(Evenement evenement) {
        try {
            String query = "UPDATE evenement SET titre=?, date=?, lieu=?, image=?, updated_at=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, evenement.getTitre());
            preparedStatement.setDate(2, new Date(evenement.getDate().getTime()));
            preparedStatement.setString(3, evenement.getLieu());
            preparedStatement.setString(4, evenement.getImage());
            preparedStatement.setTimestamp(5, evenement.getUpdated_at());
            preparedStatement.setInt(6, evenement.getId());
            preparedStatement.executeUpdate();
            System.out.println("Événement mis à jour avec succès!");
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    public void supprimer(int id) {
        try {
            String query = "DELETE FROM evenement WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Événement supprimé avec succès!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Evenement> getAll() {
        ObservableList<Evenement> evenements = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM evenement";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(resultSet.getInt("id"));
                evenement.setTitre(resultSet.getString("titre"));
                evenement.setDate(resultSet.getDate("date"));
                evenement.setLieu(resultSet.getString("lieu"));
                evenement.setImage(resultSet.getString("image"));
                evenement.setUpdated_at(resultSet.getTimestamp("updated_at"));

                evenements.add(evenement);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }

        return evenements;
    }


    public Image loadImage(String path) {
        return new Image(new File(path).toURI().toString());
    }

    public void genererPDF(Evenement evenement) {
        try {
            // Créer un nouveau document PDF
            Document document = new Document();
            OutputStream file = new FileOutputStream(new File("C:\\Users\\hamza\\OneDrive\\Bureau\\dd.pdf"));

            PdfWriter.getInstance(document, file);
            document.open();
            // Ajouter le titre de l'événement
            Paragraph p = new Paragraph("Titre: " + evenement.getTitre());
            p.setAlignment(Element.ALIGN_CENTER);
            Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            p.setFont(font);
            document.add(p);
            document.add(new Paragraph("Lieu: " + evenement.getLieu()));

            // Fermer le document et écrire le contenu dans un fichier PDF
            document.close();

            // Afficher un message de confirmation
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("PDF généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été généré avec succès.");
            alert.showAndWait();
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas de problème
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la génération du PDF: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    public Evenement getOneById(int id) {

        Evenement e = new Evenement();
        try {

            String requete = "select * from evenement where id='" + id + "'";
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                e.setTitre(rs.getString("titre"));
                e.setLieu(rs.getString("lieu"));
                e.setImage(rs.getString("image"));

                System.out.println(e.getTitre());
            }

        } catch (SQLException ex) {

        }

        return e;

    }


}
