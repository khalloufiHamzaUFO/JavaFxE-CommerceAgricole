/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author louaj
 */
public class Facture {
    private int id;
    private Date date;
    private float montant_total;  
    private String type;  
    private Comptabilite comptabilite ; 

    public Facture() {
    }

    public Facture(int id, Date date, float montant_total, String type, Comptabilite comptabilite) {
        this.id = id;
        this.date = date;
        this.montant_total = montant_total;
        this.type = type;
        this.comptabilite = comptabilite;
    }

    public Facture(Date date, float montant_total, String type, Comptabilite comptabilite) {
        this.date = date;
        this.montant_total = montant_total;
        this.type = type;
        this.comptabilite = comptabilite;
    }

  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(float montant_total) {
        this.montant_total = montant_total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
  public Comptabilite getComptabilite() {
        return comptabilite;
    }

    public void setComptabilite(Comptabilite comptabilite) {
        this.comptabilite = comptabilite;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", date=" + date + ", montant_total=" + montant_total + ", type=" + type + ", comptabilite=" + comptabilite + '}';
    }
    
}
