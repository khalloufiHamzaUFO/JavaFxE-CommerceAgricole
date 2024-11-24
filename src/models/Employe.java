/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author amine
 */
public class Employe {
   private int id ;
   private String nom ;
   private String prenom;
   private String cin  ;  
   private int age;   
   private float salaire;
   private int numtelephone ;
    public Employe() {
    }

    /* public Employe(String nom, String prenom, String cin, int age, float salaire) {
    this.nom = nom;
    this.prenom = prenom;
    this.cin = cin;
    this.age = age;
    this.salaire = salaire;
    }*/
    /*
    public Employe(int id, String nom, String prenom, String cin, int age, float salaire) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.cin = cin;
    this.age = age;
    this.salaire = salaire;
    }*/

    public Employe(int id, String nom, String prenom, String cin, int age, float salaire, int numtelephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.age = age;
        this.salaire = salaire;
        this.numtelephone = numtelephone;
    }

    public Employe(String nom, String prenom, String cin, int age, float salaire, int numtelephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.age = age;
        this.salaire = salaire;
        this.numtelephone = numtelephone;
    }


    public Employe(int id) {
        this.id = id;
    }

   


    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public int getAge() {
        return age;
    }

    /*public Employe(int i, String string, String kbaier, String amine, String string0) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Employe(int aInt, int aInt0, String string) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    public void setAge(int age) {
        this.age = age;
    }

     public int getNumtelephone() {
        return numtelephone;
    }

    public void setNumtelephone(int numtelephone) {
        this.numtelephone = numtelephone;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    /*   @Override
    public String toString() {
    return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", age=" + age + ", salaire=" + salaire + '}';
    }*/

   
    /* @Override
    public String toString() {
    return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", age=" + age + ", salaire=" + salaire + ", numtelephone=" + numtelephone  '}';
    }*/
 @Override
    public String toString() {
    return   nom +" "+prenom+" "+cin ;
    }

 
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
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
        final Employe other = (Employe) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public double getPoste() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
