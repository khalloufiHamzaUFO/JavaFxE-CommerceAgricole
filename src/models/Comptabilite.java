
package models;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Comptabilite {
    private int id;
    private Date date_comptabilite;
    private float valeur;
    private List<Facture> factures;

    public Comptabilite(int id, Date date, float valeur) {
        this.id = id;
        this.date_comptabilite = date;
        this.valeur = valeur;
        this.factures = new ArrayList<>();
    }

    public Comptabilite(Date date, float valeur) {
        this.date_comptabilite = date;
        this.valeur = valeur;
    }

    public Comptabilite() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDate_comptabilite() {
        return date_comptabilite;
    }


    public float getValeur() {
        return valeur;
    }


    public void setDate_comptabilite(Date date) {
        this.date_comptabilite = date;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    public List<Facture> getFacture() {
        return factures;
    }

    @Override
    public String toString() {
        return "Comptabilite{" + "id=" + id + ", date=" + date_comptabilite + ", valeur=" + valeur +'}';
    }


}
