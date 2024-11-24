package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import models.Categorie;
import models.Produit;
import repositories.IService;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<Produit> {

    private final Connection cnx;

    public ServiceProduit() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Produit p) {
        String query = "INSERT INTO produit(nom, prix, description, status, image, categorie_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, p.getNom());
            stmt.setFloat(2, p.getPrix());
            stmt.setString(3, p.getDescription());
            stmt.setBoolean(4, p.getStatus());
            stmt.setString(5, p.getImage());
            stmt.setInt(6, p.getCategorie().getId());
            stmt.executeUpdate();
            System.out.println("Produit created!");
        } catch (SQLException ex) {
            System.out.println("Error while adding produit: " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            int deletedRows = stmt.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Produit deleted!");
            } else {
                System.out.println("Produit with id " + id + " not found!");
            }
        } catch (SQLException ex) {
            System.out.println("Error while deleting produit: " + ex.getMessage());
        }
    }

    @SneakyThrows
    public void modifier(Produit p) {
        String query = "UPDATE produit SET nom = ?, prix = ?, description = ?, status = ?, image = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, p.getNom());
            stmt.setFloat(2, p.getPrix());
            stmt.setString(3, p.getDescription());
            stmt.setBoolean(4, p.getStatus());
            stmt.setString(5, p.getImage());
            stmt.setInt(6, p.getCategorie().getId());
            stmt.setInt(7, p.getId());
            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Produit updated!");
            } else {
                System.out.println("Produit with id " + p.getId() + " not found!");
            }
        } catch (SQLException ex) {
            System.out.println("Error while updating produit: " + ex.getMessage());
        }
    }

    @Override
    public ObservableList<Produit> getAll() {
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        String query = "SELECT * FROM produit";
        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                float prix = rs.getFloat("prix");
                String description = rs.getString("description");
                boolean status = rs.getBoolean("status");
                String image = rs.getString("image");
                int id_categorie = rs.getInt("categorie_id");
                ServiceCategorie serviceCategorie = new ServiceCategorie();
                Produit produit = new Produit(id, nom, prix, description, status, image, serviceCategorie.getById(id_categorie));
                produits.add(produit);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    @Override
    public Produit getById(int id) {
        Produit produit = null;
        try {
            String query = "SELECT * FROM produit WHERE id = ?";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                float prix = rs.getFloat("prix");
                String description = rs.getString("description");
                boolean status = rs.getBoolean("status");
                String image = rs.getString("image");
                int id_categorie = rs.getInt("categorie_id");
                ServiceCategorie serviceCategorie = new ServiceCategorie();
                Categorie categorie = serviceCategorie.getById(id_categorie);
                produit = new Produit(id, nom, prix, description, status, image, categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produit;
    }


    public List<Produit> getByCategorie(Categorie categorie) {
        List<Produit> produits = new ArrayList<>();
        try {
            String query = "SELECT * FROM produit WHERE categorie_id = ?";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, categorie.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                float prix = rs.getFloat("prix");
                String description = rs.getString("description");
                boolean status = rs.getBoolean("status");
                String image = rs.getString("image");
                Produit produit = new Produit(id, nom, prix, description, status, image, categorie);
                produits.add(produit);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }
}