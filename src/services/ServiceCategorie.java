package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Categorie;
import repositories.IService;
import utils.DataSource;

import javax.sql.StatementEvent;
import javax.swing.event.ChangeListener;
import java.sql.*;

public class ServiceCategorie implements IService<Categorie> {

    static Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Categorie c) {
        try {
            String req = "INSERT INTO `categorie` (`label`,`icon`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, c.getLabel());
            ps.setString(2, c.getIcon());

            ps.executeUpdate();
            System.out.println("Categorie created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

            String req = "DELETE FROM `categorie` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie deleted !");

    }

    @Override
    public void modifier(Categorie c) {
        try {
            System.out.println(c);
            String req = "UPDATE `categorie` SET `label` = ?, `icon` = ? WHERE `categorie`.`id` = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, c.getLabel());
            ps.setString(2, c.getIcon());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
            System.out.println("Categorie updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<Categorie> getAll() {
        ObservableList<Categorie> list = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM categorie";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Categorie c = new Categorie(rs.getInt("id"), rs.getString("label"), rs.getString("icon"));
                list.add(c);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public Categorie getById(int id) {
        try {
            String req = "SELECT * FROM categorie WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("label"), rs.getString("icon"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Categorie getByLabel(String label) {
        try {
            String req = "SELECT * FROM categorie WHERE label = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, label);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("label"), rs.getString("icon"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
