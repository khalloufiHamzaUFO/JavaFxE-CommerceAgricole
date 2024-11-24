/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Culture;
import models.Terrain;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author USER
 */
public class CultureCRUD {

  
    Connection cnx = DataSource.getInstance().getCnx();
      public void ajouter(Culture c) {
        try {
            String req = "INSERT INTO `culture` (`type`,`date_planting`, `quantite`) VALUES ('" + c.getType() +"', '" + c.getDate_planting()+"', '" + c.getQuantite() + "')";
            Statement st = cnx.createStatement();//pour ramener la requette au sgbd
            st.executeUpdate(req); //executé la requette (update pour les requettes de: insert,update,delete),query(requette de select)
            System.out.println("culture ajoutée !");
            //cnx.close(); // fermer la connexion
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /* public void ajouter(Culture c) {
    try {
    String req = "INSERT INTO `culture` (`id`, `type`,`date_planting`, `quantite`) VALUES ('" + c.getId() + "', '" + c.getType() +"', '" + c.getDate_planting()+"', '" + c.getQuantite() + "')";
    Statement st = cnx.createStatement();//pour ramener la requette au sgbd
    st.executeUpdate(req); //executé la requette (update pour les requettes de: insert,update,delete),query(requette de select)
    System.out.println("culture ajoutée !");
    //cnx.close(); // fermer la connexion
    } catch (SQLException ex) {
    System.out.println(ex.getMessage());
    }
    }*/
   public void ajouter2(Culture c) {
    try {
        String req = "INSERT INTO `culture` (`type`,`date_planting`, `quantite`) VALUES (?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
       // ps.setInt(1, c.getId());
        ps.setString(1, c.getType());
        ps.setDate(2, new java.sql.Date(c.getDate_planting().getTime()));
        ps.setFloat(3, c.getQuantite());
        ps.executeUpdate();
        System.out.println("culture ajoutée !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
   /* public void ajouter2(Culture c) {
   try {
   String req = "INSERT INTO `culture` (`id`, `type`,`date_planting`, `quantite`) VALUES (?,?,?,?)";
   PreparedStatement ps = cnx.prepareStatement(req);
   ps.setInt(1, c.getId());
   ps.setString(2, c.getType());
   ps.setDate(3, new java.sql.Date(c.getDate_planting().getTime()));
   ps.setFloat(4, c.getQuantite());
   ps.executeUpdate();
   System.out.println("culture ajoutée !");
   } catch (SQLException ex) {
   System.out.println(ex.getMessage());
   }
   }*/
  
   public void supprimer(Culture c) {
   try {
       String req = "DELETE FROM `culture` WHERE id = " + c.getId();
       Statement st = cnx.createStatement();
       st.executeUpdate(req);
       System.out.println("culture supprimée !");
   } catch (SQLException ex) {
       System.out.println(ex.getMessage());
   }
}
   /* // public void supprimer(int id) {
   public void supprimer(Culture c) {
   try {
   String req = "UPDATE `culture` SET `quantite` = '" + c.getQuantite() + "', `type` = '" + c.getType() + "', `date_planting` = '" + c.getDate_planting() + "' WHERE `culture`.`id` = " + c.getId();
   
   // String req = "DELETE FROM `culture` WHERE id = " + id;
   Statement st = cnx.createStatement();
   st.executeUpdate(req);
   System.out.println("culture supprimée !");
   } catch (SQLException ex) {
   System.out.println(ex.getMessage());
   }
   }
   */
    public void modifier(Culture c) {
        try {
            String req = "UPDATE `culture` SET `quantite` = '" + c.getQuantite() + "', `type` = '" + c.getType() + "', `date_planting` = '" + c.getDate_planting() + "' WHERE `culture`.`id` = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("culture modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /* public void modifier2(Culture c) {
    try {
    // Vérifier si le terrain existe dans la base de données
    String selectReq = "SELECT id FROM culture WHERE id = ?";
    PreparedStatement selectPs = cnx.prepareStatement(selectReq);
    selectPs.setInt(1, c.getId());
    ResultSet selectResult = selectPs.executeQuery();
    
    if (!selectResult.next()) {
    System.out.println("Erreur: culture avec ID " + c.getId() + " n'existe pas dans la base de données !");
    return;
    }
    
    // Modifier le terrain
    String updateReq = "UPDATE culture SET type = ?, quantite = ?,  date_planting = ? WHERE id = ?";
    PreparedStatement updatePs = cnx.prepareStatement(updateReq);
    updatePs.setString(1, c.getType());
    updatePs.setFloat(2, c.getQuantite());
    
    updatePs.setDate(2, new java.sql.Date(c.getDate_planting().getTime()));
    
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
    */
    public void modifier2(Culture c) {
    try {
        // Vérifier si la culture existe dans la base de données
        String selectReq = "SELECT id FROM culture WHERE id = ?";
        PreparedStatement selectPs = cnx.prepareStatement(selectReq);
        selectPs.setInt(1, c.getId());
        ResultSet selectResult = selectPs.executeQuery();
        
        if (!selectResult.isBeforeFirst()) {
            System.out.println("Erreur: culture avec ID " + c.getId() + " n'existe pas dans la base de données !");
            return;
        }
        
        // Modifier la culture
        String updateReq = "UPDATE culture SET type = ?, quantite = ?, date_planting = ? WHERE id = ?";
        PreparedStatement updatePs = cnx.prepareStatement(updateReq);
        updatePs.setString(1, c.getType());
        updatePs.setFloat(2, c.getQuantite());
        updatePs.setDate(3, new java.sql.Date(c.getDate_planting().getTime()));
        updatePs.setInt(4, c.getId());
        
        int result = updatePs.executeUpdate();//ResultSet c'est le contenaire dans lequel on a insérer  executeUpdate
        
        if (result == 1) {
            System.out.println("Culture modifiée !");
        } else {
            System.out.println("Erreur: Culture non modifiée !");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur: " + ex.getMessage());
    }
}


    public List<Culture> afficher() {
        List<Culture> list = new ArrayList<>();
        try {
            String req = "Select * from culture";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req); //ResultSet c'est le contenaire dans lequel on a insérer  executeQuery
            
            while(rs.next()){ //tantqu'il y a toujours des données
                Culture c = new Culture();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));//injecter les valeurs qui se trouve dans la ligne de rs dans l'objet c qui a été créer
                c.setQuantite(rs.getFloat("quantite"));
                c.setDate_planting(rs.getDate("date_planting"));
                list.add(c);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
     
  public List<Culture> trier(String type) throws SQLException {
        ArrayList<Culture> listCulture = new ArrayList<>();

        String requete = null;
        if (type.equals("Trie type")) {
            requete = "SELECT * from culture ORDER by type "; //MAJUSCULE NON OBLIGATOIRE 
        } else if (type.equals("Trie quantite")) {
            requete = "SELECT * from culture ORDER by quantite"; //MAJUSCULE NON OBLIGATOIRE 
        } else if (type.equals("Trie date_planting")) {
            requete = "SELECT * from culture ORDER by date_planting";
        } else if (type.equals("Tout")) {
            requete = "SELECT * from culture";
        }
        PreparedStatement st = cnx.prepareStatement(requete);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Culture c = new Culture();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));//injecter les valeurs qui se trouve dans la ligne de rs dans l'objet c qui a été créer
                c.setQuantite(rs.getFloat("quantite"));
                c.setDate_planting(rs.getDate("date_planting"));
                listCulture.add(c);
        }
        return listCulture;

    }
  
    public Culture affichercul(int id) throws SQLException{
         String req= "SELECT * FROM categorie where id=1";
         PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
       
           Culture c = new Culture();
        if(rs.next()){
        
             c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));//injecter les valeurs qui se trouve dans la ligne de rs dans l'objet c qui a été créer
                c.setQuantite(rs.getFloat("quantite"));
                c.setDate_planting(rs.getDate("date_planting"));
            String requet= "SELECT * FROM produit where culture_id = 1 ";
         PreparedStatement pst = cnx.prepareStatement(requet);
        ResultSet rst = pst.executeQuery();
      // ps.setInt(1, idCategorie);
          List<Terrain> tt = new ArrayList<>();
        while(rst.next()){
           Terrain t = new Terrain();
           t.setId(rs.getInt("id"));
                t.setNumero(rs.getInt("numero"));
                t.setSurface(rs.getInt("surface"));
                t.setLieu(rs.getString("lieu"));
                t.setImage(rs.getString("image"));

            System.out.println(t);
         
            tt.add(t);
        }
            c.setTerrain(tt);
        }
        return c;
  
    }
     public Culture afficher2(int idCulture) {
    
    Culture culture = null;
    try {
    PreparedStatement ps = cnx.prepareStatement("SELECT * FROM culture WHERE id_culture = ?");
    ps.setInt(1, idCulture);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){ //tantqu'il y a toujours des données
    Culture c = new Culture();
    c.setType(rs.getString("type"));//injecter les valeurs qui se trouve dans la ligne de rs dans l'objet c qui a été créer
    c.setQuantite(rs.getFloat("quantite"));
    c.setDate_planting(rs.getDate("date_planting"));
    }
    } catch (SQLException ex) {
    System.out.println("Error during SELECT in table culture: " + ex.getMessage());
    }
    return culture;
    }
    public Culture getById(int id) {
        try {
            String req = "SELECT * FROM culture WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Culture(rs.getInt("id"), rs.getString("type"), rs.getDate("date_planting"),rs.getFloat("quantite"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

   
    }

