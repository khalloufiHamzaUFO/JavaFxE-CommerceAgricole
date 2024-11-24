package models;

public class Equipement {
    private int id;
    private Employe employe;
    private String Type;
    private String Marque;
    private int disponnible;
    private String etat;
    private String matricule;


    public Equipement() {
    }

    //public Equipement(int id, int employe_id, String Type, String Marque, Boolean disponible, String etat, String matricule) {
    public Equipement(int id, Employe employe, String Type, String Marque, Boolean disponnible, String etat, String matricule) {
        this.id = id;
        // this.employe_id = employe_id;
        this.employe = employe;
        this.Type = Type;
        this.Marque = Marque;
        this.disponnible = disponnible ? 1 : 0;
        this.etat = etat;
        this.matricule = matricule;
    }

    public Equipement(Employe employe, String Type, String Marque, Boolean disponnible, String etat, String matricule) {
        this.employe = employe;
        this.Type = Type;
        this.Marque = Marque;
        this.disponnible = disponnible ? 1 : 0;
        this.etat = etat;
        this.matricule = matricule;
    }


    public int getId() {
        return id;
    }

    public String getType() {
        return Type;
    }

    public String getMarque() {
        return Marque;
    }

    public int isDisponnible() {
        return disponnible;
    }

    public String getEtat() {
        return etat;
    }

    public String getMatricule() {
        return matricule;
    }

    //public int getEmploye_id() {
    //  return employe_id;
    // }
    public Employe getEmploye_() {
        return employe;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setMarque(String Marque) {
        this.Marque = Marque;
    }

    public void setDisponnible(Boolean disponnible) {
        this.disponnible = disponnible ? 1 : 0;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    // public void setEmploye_id(int employe_id) {
    //   this.employe_id = employe_id;
    public void setId(int id) {
        this.id = id;
    }

    //}
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    @Override
    public String toString() {
        return "Equipement{" + "id=" + id + ", employe=" + employe + ", Type=" + Type + ", Marque=" + Marque + ", disponnible=" + disponnible + ", etat=" + etat + ", matricule=" + matricule + '}';
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
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
        final Equipement other = (Equipement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }


}
