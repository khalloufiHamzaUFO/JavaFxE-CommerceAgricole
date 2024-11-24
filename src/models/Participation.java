package models;


public class Participation {
    public int id;
    public int  id_utilisateur_id;
    public int evenement_id;

    public Participation() {

    }

    public Participation(int id_utilisateur_id, int evenement_id) {
        this.id_utilisateur_id = id_utilisateur_id;
        this.evenement_id = evenement_id;
    }

    public Participation(int id, int id_utilisateur_id, int evenement_id) {
        this.id = id;
        this.id_utilisateur_id = id_utilisateur_id;
        this.evenement_id = evenement_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utilisateur_id() {
        return id_utilisateur_id;
    }

    public void setId_utilisateur_id(int id_utilisateur_id) {
        this.id_utilisateur_id = id_utilisateur_id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        hash = 79 * hash + this.id_utilisateur_id;
        hash = 79 * hash + this.evenement_id;
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
        final Participation other = (Participation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_utilisateur_id != other.id_utilisateur_id) {
            return false;
        }
        if (this.evenement_id != other.evenement_id) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Participation{" + "id=" + id + ", id_utilisateur_id=" + id_utilisateur_id + ", evenement_id=" + evenement_id +  '}';
    }





}