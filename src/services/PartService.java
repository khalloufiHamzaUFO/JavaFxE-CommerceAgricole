package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Participation;
import repositories.IService;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PartService implements IService {
    Connection cnx = DataSource.getInstance().getCnx();


    public void ajouter(Participation p) {
        Participation pt = p;
        try {
            String sql = "insert into participation(id_utilisateur_id,evenement_id)values ('"+p.getId_utilisateur_id()+"','"+p.getEvenement_id()+"')";
            PreparedStatement ste =  cnx.prepareStatement(sql);
            ste.executeUpdate(sql);
            System.out.println("Votre participation est ajouté avec succés!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(int id) {

        try {
            String sql = "DELETE FROM participation WHERE id='"+id+"'";
            PreparedStatement ste = cnx.prepareStatement(sql);
            int rowsAffected = ste.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun enregistrement supprimé dans la base de données.");
            } else {
                System.out.println("Enregistrement supprimé avec succès de la base de données.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de l'événement: " + ex.getMessage());
        }
    }

    public void modifier(Participation p) {
        System.out.println(p.getId());
        try {
            String sqlup = "UPDATE `participation` SET `id_utilisateur_id`=?, `image`=evenement_id WHERE `id`='"+p.getId()+"'";
            PreparedStatement ste = cnx.prepareStatement(sqlup);
            ste.setString(1, Integer.toString(p.getId_utilisateur_id()));
            ste.setString(2, Integer.toString(p.getEvenement_id()));
            ste.executeUpdate();
            int rowsAffected = ste.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun enregistrement mis à jour dans la base de données.");
            } else {
                System.out.println("Enregistrement mis à jour avec succès dans la base de données.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour de l'événement: " + ex.getMessage());
        }
    }

    public ObservableList getAllPart() {
        ObservableList<Participation> list = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM participation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Participation p = new Participation(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null; // or return an empty list: return new ArrayList<>();
        }
        return list;
    }

    @Override
    public Object getById(int p) {
        return null;
    }



    public Participation getOneById(int id) {

        Participation p = new Participation();
        try {
            String requete = "select * from participation where titre='" + id + "'";
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                p.setId_utilisateur_id(Integer.parseInt(rs.getString("Id_utilisateur_id")));
                p.setEvenement_id(Integer.parseInt(rs.getString("evenement_id")));
            }
        } catch (SQLException ex) {

        }

        return p;

    }

    @Override
    public void ajouter(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList getAll() {
        return null;
    }


}








