package services;

import javafx.scene.control.Alert;
import models.user.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import repositories.IIService;
import repositories.IService;
import utils.DataSource;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IIService<Utilisateur> {


    Connection cnx = DataSource.getInstance().getCnx();
    List<Utilisateur> users = new ArrayList<>();

    public Utilisateur Signin(String email, String password) {
        try {
            String req5 = "SELECT * FROM utilisateur WHERE email = ?";
            PreparedStatement pr5 = cnx.prepareStatement(req5);
            pr5.setString(1, email);
            ResultSet rs = pr5.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String mot = storedPassword.replaceFirst("^\\$2y\\$", "\\$2a\\$");

                if (BCrypt.checkpw(password, mot)) {
                    Utilisateur u = new Utilisateur();
                    u.setId(rs.getInt("id"));

                    u.setEmail(rs.getString("email"));
                    System.out.println(u);
                    try {
                        FileWriter writer = new FileWriter("session.txt", false);

                        writer.write(String.valueOf(u.getId()));
                        writer.flush();
                        writer.close();
                        return u;
                    } catch (IOException ex) {

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur login");
                    alert.setHeaderText("Mot de passe incorrecte");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur login");
                alert.setHeaderText("Utilisateur existe pas !");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur login");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        }

        return null;
    }


    @Override
    public void ajouter(Utilisateur u) {
        Utilisateur p = u;
        try {
            String req = "SELECT * FROM utilisateur  where email = ?";
            PreparedStatement pr = cnx.prepareStatement(req);
            pr.setString(1, p.getEmail());
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Utilisateur existe déja !");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        String password = u.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        try {
            String req = "INSERT INTO `utilisateur`(`email`, `roles`, `password`, `nom`, `prenom`, `telephone`, `cin`, `is_verified`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getRoles());
            ps.setString(3, hashedPassword);
            ps.setString(4, u.getNom());
            ps.setString(5, u.getPrenom());
            ps.setString(6, u.getTelephone());
            ps.setInt(7, u.getCin());
            ps.setByte(8, u.getIsVerified());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(Utilisateur user) throws SQLException {
        try {
            String req = "DELETE FROM `utilisateur` WHERE id = ? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, user.getId());
            st.executeUpdate();
            System.out.println("Utilisateur supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerUtilisateur(Utilisateur user) {
        try {
            String requete = "delete from utilisateur where nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.executeUpdate();

            System.out.println("Utlisateur est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(Utilisateur u) throws SQLException {
        try {
            String updatereq = "UPDATE `utilisateur` SET `roles`=?,`password`=?,`nom`=? ,`prenom`=?, `telephone`=?, `cin`=? WHERE `email`=? ";
            PreparedStatement pr2 = cnx.prepareStatement(updatereq);
            pr2.setString(1, u.getRoles());
            pr2.setString(2, u.getPassword());
            pr2.setString(3, u.getNom());
            pr2.setString(4, u.getPrenom());
            pr2.setString(5, u.getTelephone());
            pr2.setInt(6, u.getCin());
            pr2.setString(7, u.getEmail());

            pr2.executeUpdate();
            System.out.println("Utilisateur modifié avec succès");


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Utilisateur getUserById(int id) {
        Utilisateur utilisateur = null;
        try {
            String selectreq = "SELECT * FROM `utilisateur` WHERE `id`=?";
            PreparedStatement pr = cnx.prepareStatement(selectreq);
            pr.setInt(1, id);

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setRoles(rs.getString("roles"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setCin(rs.getInt("cin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return utilisateur;
    }



    public void modifier1(Utilisateur u) throws SQLException {
        try {
            String updatereq = "UPDATE `utilisateur` SET `nom`=? ,`prenom`=?, `telephone`=? WHERE `email`=? ";
            PreparedStatement pr2 = cnx.prepareStatement(updatereq);


            pr2.setString(1, u.getNom());
            pr2.setString(2, u.getPrenom());
            pr2.setString(3, u.getTelephone());

            pr2.setString(4, u.getEmail());

            pr2.executeUpdate();
            System.out.println("Utilisateur modifié avec succès");


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM utilisateur ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setEmail(rs.getString("email"));
                u.setRoles(rs.getString("roles"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setTelephone(rs.getString("telephone"));
                u.setCin(rs.getInt("cin"));

                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Utilisateur getOneById(int id) throws SQLException {
        Utilisateur u = new Utilisateur();

        try {
            String req4 = "SELECT * FROM `utilisateur` WHERE id = ? ";
            PreparedStatement pr4 = cnx.prepareStatement(req4);
            pr4.setInt(1, id);
            ResultSet rs = pr4.executeQuery();
            if (rs.next()) {
                u.setEmail(rs.getString("email"));
                u.setRoles(rs.getString("roles"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setTelephone(rs.getString("telephone"));
                u.setCin(rs.getInt("cin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    @Override
    public boolean verifierEmail(String email) {

        try {
            String req = "SELECT * FROM utilisateur where email = ?";
            PreparedStatement pr = cnx.prepareStatement(req);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Verifier adresse");
                alert.setHeaderText("Veuillez saisir une adresse mail valide");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }


    public boolean resetPassword(String email, String newPassword) throws SQLException {


        String sql = "UPDATE utilisateur SET password=? WHERE email=?";
        PreparedStatement statement = cnx.prepareStatement(sql);

        statement.setString(1, newPassword);
        statement.setString(2, email);


        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Un existant Utilisateur a reccupérer son mot de passe !");
        }
        return true;
    }


}
