package services;

import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Comptabilite;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ComptabiliteCRUD {
    Connection cnx2;

    public ComptabiliteCRUD() {
        cnx2 = (Connection) DataSource.getInstance().getCnx();
    }


    public void ajouterComptabilite(Comptabilite c) {
        try {

            String requete = "INSERT INTO `comptabilite` (`date_comptabilite`,`valeur`) VALUES ('" + c.getDate_comptabilite() + "', '" + c.getValeur() + "')";
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Comptabilite Ajoute");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `comptabilite` WHERE id = " + id;
            Statement st = cnx2.createStatement();
            st.executeUpdate(req);
            System.out.println("comptabilite supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Comptabilite c) {
        try {
            String req = "UPDATE `comptabilite` SET `date_comptabilite` = '" + c.getDate_comptabilite() + "',  `valeur` = '" + c.getValeur() + "' WHERE `comptabilite`.`id` = " + c.getId();
            Statement st = cnx2.createStatement();
            st.executeUpdate(req);
            System.out.println("comptabilite modifiée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Comptabilite getByDate(Date date) {
        Comptabilite c = new Comptabilite();
        try {
            String req = "SELECT * FROM comptabilite WHERE date_comptabilite=?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setDate(1, new Date(date.getTime()));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setDate_comptabilite(rs.getDate("date_comptabilite"));
                c.setValeur(rs.getFloat("valeur"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }


    public Comptabilite getById(int id) {
        Comptabilite c = new Comptabilite();
        try {
            String req = "SELECT * FROM comptabilite WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setDate_comptabilite(rs.getDate("date_comptabilite"));
                c.setValeur(rs.getFloat("valeur"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }

    public ObservableList<Comptabilite> getAll() {
        ObservableList<Comptabilite> list = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM comptabilite";
            PreparedStatement st = cnx2.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Comptabilite c = new Comptabilite(rs.getInt("id"), rs.getDate("date_comptabilite"), rs.getFloat("valeur"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}
