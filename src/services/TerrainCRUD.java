package services;


import models.Culture;
import models.Terrain;
import utils.DataSource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author USER
 */
public class TerrainCRUD {
    Connection cnx = DataSource.getInstance().getCnx();

    /*public  void ajouter(Terrain t) {
    try {
    //String req = "INSERT INTO `terrain` (`id`, `culture_id`, `numero`, `surface`, `lieu`) VALUES ('" + t.getId() + "', '" + t.getCulture().getId() + "', '" + t.getNumero() + "', '" + t.getSurface() + "', '" + t.getLieu() + "')";
    String req = "INSERT INTO `terrain` (`culture_id`, `numero`, `surface`, `lieu`) VALUES ('" + t.getCulture().getId() + "', '" + t.getNumero() + "', '" + t.getSurface() + "', '" + t.getLieu() + "')";

    Statement st = cnx.createStatement();//pour ramener la requette au sgbd
    int result =st.executeUpdate(req); //executé la requette (update pour les requettes de: insert,update,delete),query(requette de select)
    if(result == 1){
    System.out.println("Terrain ajoutée !");
    }else{
    System.out.println("Erreur: Terrain non ajouté !");
    }
    //cnx.close(); // fermer la connexion
    } catch (SQLException ex) {
    System.out.println("Erreur: "+ex.getMessage());
    }
    }*/
 /*public void ajouter(Terrain t) {
 try {
 String req1 = "SELECT * FROM culture";
 String req = "INSERT INTO terrain (culture_id, numero, surface, lieu) VALUES (?, ?, ?, ?)";
 PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
 ResultSet rs = ps.executeQuery(req1);
 ps.setInt(1, t.getCulture().getId());
 int culture_id = rs.getInt("id");
 Culture c = new Culture();
 c.setId(culture_id);
 
 t.setCulture(c);
 ps.setInt(2, t.getNumero());
 ps.setInt(3, t.getSurface());
 ps.setString(4, t.getLieu());
 int result = ps.executeUpdate();
 if (result == 1) {
 System.out.println("Terrain ajouté !");
 ResultSet rs = ps.getGeneratedKeys();
 if (rs.next()) {
 int id = rs.getInt(1);
 t.setId(id);
 }
 } else {
 System.out.println("Erreur: Terrain non ajouté !");
 }
 } catch (SQLException ex) {
 System.out.println("Erreur: " + ex.getMessage());
 }
 }*/
    public void ajouter(Terrain t) {
        try {
            String req = "INSERT INTO `terrain` (`culture_id`, `numero`, `surface`, `lieu`, `image`) VALUES (?, ?, ?, ?,?)";
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getCulture().getId());
            ps.setInt(2, t.getNumero());
            ps.setInt(3, t.getSurface());
            ps.setString(4, t.getLieu());
            ps.setString(5, t.getImage());
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Terrain ajouté !");
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    t.setId(id);
                }
            } else {
                System.out.println("Erreur: Terrain non ajouté !");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    public void ajouterim(Terrain t, String imagePath) throws IOException {
        try {
            // lire l'image à partir du fichier
            File imageFile = new File("src/edu/esprit/gui/image/" + t.getImage());
            // File imageFile = new File("src/image/" + t.getImage());
            BufferedImage image = ImageIO.read(imageFile);

            // préparer la requête SQL pour insérer le terrain
            String req = "INSERT INTO `terrain` (`culture_id`, `numero`, `surface`, `lieu`, `image`) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getCulture().getId());
            ps.setInt(2, t.getNumero());
            ps.setInt(3, t.getSurface());
            ps.setString(4, t.getLieu());

            // convertir l'image en tableau de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

            // ajouter l'image à la requête SQL
            ps.setBinaryStream(5, bais, imageData.length);

            // exécuter la requête SQL
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Terrain ajouté !");
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    t.setId(id);
                }
            } else {
                System.out.println("Erreur: Terrain non ajouté !");
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }


    public void ajouter2(Terrain t) {
        try {

            String req = "INSERT INTO `terrain` (`culture_id`, `numero`, `surface`, `lieu`, `image`) VALUES (?, ?, ?, ?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            // ps.setInt(1, t.getId());
            ps.setInt(1, t.getCulture().getId());
            ps.setInt(2, t.getNumero());
            ps.setInt(3, t.getSurface());
            ps.setString(4, t.getLieu());
            // ps.setString(5, t.getImage());
            ps.setString(5, t.getImage());
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Terrain ajouté !");
            } else {
                System.out.println("Erreur: Terrain non ajouté !");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    /* public void ajouter2(Terrain t) {
    try {
    String req = "INSERT INTO `terrain` (`id`, `culture_id`, `numero`, `surface`, `lieu`) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, t.getId());
    ps.setInt(2, t.getCulture().getId());
    ps.setInt(3, t.getNumero());
    ps.setInt(4, t.getSurface());
    ps.setString(5, t.getLieu());
    int result =ps.executeUpdate();
    if(result == 1){
    System.out.println("Terrain ajouté !");
    }else{
    System.out.println("Erreur: Terrain non ajouté !");
    }
    } catch (SQLException ex) {
    System.out.println("Erreur: "+ex.getMessage());
    }
    }*/
     /*public void ajouter2(Terrain t) {
     try {
     String req = "INSERT INTO `terrain` (`id`,`culture_id`, `numero`,`surface`, `lieu`) VALUES (?,?,?,?,?)";
     PreparedStatement ps = cnx.prepareStatement(req);
     ps.setInt(1, t.getId());
     ps.setInt(2, t.getCulture().get(0).getId()); // ici j'ai supposé que chaque terrain a une seule culture associée
     ps.setInt(3, t.getNumero());
     ps.setInt(4, t.getSurface());
     ps.setString(5, t.getLieu());
     ps.executeUpdate();
     System.out.println("culture ajoutée !");
     } catch (SQLException ex) {
     System.out.println(ex.getMessage());
     }
     }*/
    // public void supprimer(int id) {
   /*  public void supprimer2(Terrain t) {
   try {String req = "UPDATE `terrain` SET `culture_id` = ?, `numero` = ?, `surface` = ?, `lieu` = ? WHERE `terrain`.`id` = ?";
   
   //String req = "DELETE FROM `terrain` WHERE id = " + id;
   Statement st = cnx.createStatement();
   int result = st.executeUpdate(req);
   if(result == 1){
   System.out.println("Terrain supprimé !");
   }else{
   System.out.println("Erreur: Terrain non supprimé !");
   }
   } catch (SQLException ex) {
   System.out.println("Erreur: "+ex.getMessage());
   }
   }*/
    public void supprimer(Terrain t) {
        try {
            String req = "DELETE FROM terrain WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId());
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Terrain supprimé !");

            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    public void supprimer3(int id) {
        try {
            String req = "DELETE FROM `terrain` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("terrain supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer2(Terrain t) {
        try {
            String req = "DELETE FROM `terrain` WHERE id = " + t.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("terrain supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* public void modifier(Terrain t) {
    try {
    String req = "UPDATE `terrain` SET `culture_id` = ?, `numero` = ?, `surface` = ?, `lieu` = ? WHERE `terrain`.`id` = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, t.getCulture().getId());
    ps.setInt(2, t.getNumero());
    ps.setInt(3, t.getSurface());
    ps.setString(4, t.getLieu());
    ps.setInt(5, t.getId());
    int result = ps.executeUpdate();
    if(result == 1){
    System.out.println("Terrain modifié !");
    }else{
    System.out.println("Erreur: Terrain non modifié !");
    }
    } catch (SQLException ex) {
    System.out.println("Erreur: "+ex.getMessage());
    }
    }*/
       /*public void modifier(Terrain t) {
       try {
       String req = "UPDATE terrain SET culture_id = ?, numero = ?, surface = ?, lieu = ? WHERE id = ?";
       PreparedStatement ps = cnx.prepareStatement(req);
       ps.setInt(1, t.getCulture().getId());
       ps.setInt(2, t.getNumero());
       ps.setInt(3, t.getSurface());
       ps.setString(4, t.getLieu());
       ps.setInt(5, t.getId());
       int result = ps.executeUpdate();
       if (result == 1) {
       System.out.println("Terrain modifié !");
       } else if (result == 0) {
       System.out.println("Erreur: Terrain avec ID " + t.getId() + " n'existe pas dans la base de données !");
       } else {
       System.out.println("Erreur: Modification du terrain avec ID " + t.getId() + " a échoué !");
       }
       } catch (SQLException ex) {
       System.out.println("Erreur: " + ex.getMessage());
       }
       }*/
    public void modifier(Terrain t) {
        try {
            // Vérifier si le terrain existe dans la base de données
            String selectReq = "SELECT id FROM terrain WHERE id = ?";
            PreparedStatement selectPs = cnx.prepareStatement(selectReq);
            selectPs.setInt(1, t.getId());
            ResultSet selectResult = selectPs.executeQuery();

            if (!selectResult.next()) {
                System.out.println("Erreur: Terrain avec ID " + t.getId() + " n'existe pas dans la base de données !");
                return;
            }

            // Modifier le terrain
            String updateReq = "UPDATE terrain SET culture_id = ?, numero = ?, surface = ?, lieu = ?, image = ? WHERE id = ?";
            PreparedStatement updatePs = cnx.prepareStatement(updateReq);
            updatePs.setInt(1, t.getCulture().getId());
            updatePs.setInt(2, t.getNumero());
            updatePs.setInt(3, t.getSurface());
            updatePs.setString(4, t.getLieu());
            updatePs.setString(5, t.getImage());
            updatePs.setInt(6, t.getId());
            int result = updatePs.executeUpdate();

            if (result == 1) {
                System.out.println("Terrain modifié !");
            } else {
                System.out.println("Erreur: Terrain non modifié !");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    /*public void modifier2(Terrain t) {
    try {
    String req = "UPDATE `terrain` SET `culture_id` = '" + t.getCulture() + "', `numero` = '" + t.getNumero() + "', `surface` = '" + t.getSurface() +"', `lieu` = '" + t.getLieu() + "' WHERE `terrain`.`id` = " + t.getId();
    Statement st = cnx.createStatement();
    st.executeUpdate(req);
    System.out.println("terrain modifiée !");
    } catch (SQLException ex) {
    System.out.println(ex.getMessage());
    }
    }*/
    public List<Terrain> afficher() {
        List<Terrain> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM terrain";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setId(rs.getInt("id"));
                t.setNumero(rs.getInt("numero"));
                t.setSurface(rs.getInt("surface"));
                t.setLieu(rs.getString("lieu"));
                t.setImage(rs.getString("image"));
                int culture_id = rs.getInt("culture_id");
                //String culture_id = rs.getString("culture_id");


                CultureCRUD cultureCRUD = new CultureCRUD();
                Culture culture = cultureCRUD.getById(culture_id);
                t.setCulture(culture);
                //Culture c = new Culture();
                //c.setId(culture_id);
                // c.setType(culture_id);
                //c.setType(rs.getString("culture_id"));

                //t.setCulture(c);
                list.add(t);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        return list;
    }

}


