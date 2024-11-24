package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Comptabilite;
import models.Facture;
import utils.DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author louaj
 */
public class FactureCRUD {
    Connection cnx2;
    ComptabiliteCRUD comptabiliteCRUD =new ComptabiliteCRUD();

    public FactureCRUD() {
        cnx2 = (Connection) DataSource.getInstance().getCnx();
    }


    public void updateFacture(Facture f) {
        try {
            String req = "UPDATE facture SET comptabilite_id=?, date_facture=?, type=?, montant_totale=? WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, f.getComptabilite().getId());
            pst.setDate(2, (Date) f.getDate());
            pst.setString(3, f.getType());
            pst.setFloat(4, f.getMontant_total());
            pst.setInt(5, f.getId());
            pst.executeUpdate();
            System.out.println("Facture mise à jour !");
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    public void ajouterFacture(Facture f) {
        try {
            String req = "INSERT INTO `facture` (`comptabilite_id`, `date_facture`, `type`, `montant_totale`) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx2.prepareStatement(req);
            ps.setInt(1, f.getComptabilite().getId());
            ps.setDate(2, new Date(f.getDate().getTime()));
            ps.setString(3, f.getType());
            ps.setFloat(4, f.getMontant_total());
            ps.executeUpdate();
            System.out.println("Facture ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `facture` WHERE id = " + id;
            Statement st = cnx2.createStatement();
            st.executeUpdate(req);
            System.out.println("facture supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Facture> afficherFacture() {
        ObservableList<Facture> myList = FXCollections.observableArrayList();

        try {
            String req4 = "SElECT * FROM facture";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(req4);
            while (rs.next()) {
                Facture f = new Facture();
                f.setId(rs.getInt(1));
                int comptabilite_id = rs.getInt("comptabilite_id");
                f.setDate(rs.getDate("date_facture"));
                f.setType(rs.getString("type"));
                f.setMontant_total(rs.getFloat("montant_totale"));
                    // here
                Comptabilite c = comptabiliteCRUD.getById(comptabilite_id);
                f.setComptabilite(c);
                myList.add(f);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        return myList;
    }
}
