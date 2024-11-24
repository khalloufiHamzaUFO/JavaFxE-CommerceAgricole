/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author USER
 */

public class Culture {
   
    private int id; 
    private String type;  
    private Date date_planting ;
    private float quantite;
    private Collection terrain;
    
    public Culture() {
    }

    public Culture(int id) {
        this.id = id;
    }

    public Culture(int id, String type, Date date_planting, float quantite, Collection terrain) {
        this.id = id;
        this.type = type;
        this.date_planting = date_planting;
        this.quantite = quantite;
        this.terrain = terrain;
    }

    public Culture(int id, String type) {
        this.id = id;
        this.type = type;
    }

    /* public Culture(int id, String type, Date date_planting, float quantite, Terrain terrain) {
    this.id = id;
    this.type = type;
    this.date_planting = date_planting;
    this.quantite = quantite;
    this.terrain = terrain;
    }*/

    public Culture(int id, String type, Date date_planting, float quantite) {
        this.id = id;
        this.type = type;
        this.date_planting = date_planting;
        this.quantite = quantite;
    }

    public Culture(String type, Date date_planting, float quantite) {
        this.type = type;
        this.date_planting = date_planting;
        this.quantite = quantite;
       
    }

    public Culture(String type) {
        this.type = type;
    }

   
    

    public int getId() {
        return id;
    }
public void setId(int id) {
        this.id = id;
    }

   
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public Date getDate_planting() {
        return date_planting;
    }

    public void setDate_planting(Date date_planting) {
        this.date_planting = date_planting;
    }

    public float getQuantite() {
        return quantite;
    }

    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }

    /* public Terrain getTerrain() {
    return terrain;
    }
    
    public void setTerrain(Terrain terrain) {
    this.terrain = terrain;
    }*/

    public void setTerrain(Collection terrain) {
        this.terrain = terrain;
    }

    public Collection getTerrain() {
        return terrain;
    }
    
    /* @Override
    public String toString() {
    return "Culture{" + "id=" + id + ", type=" + type + ", date_planting=" + date_planting + ", quantite=" + quantite + ", terrain=" + terrain + '}';
    }*/

     @Override
    public String toString() {
    return "Culture{" + type  + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + Objects.hashCode(this.date_planting);
        hash = 59 * hash + Float.floatToIntBits(this.quantite);
        hash = 59 * hash + Objects.hashCode(this.terrain);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Culture other = (Culture) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.quantite) != Float.floatToIntBits(other.quantite)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.date_planting, other.date_planting)) {
            return false;
        }
        if (!Objects.equals(this.terrain, other.terrain)) {
            return false;
        }
        return true;
    }


   
}
