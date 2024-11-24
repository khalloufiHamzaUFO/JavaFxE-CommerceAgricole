package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javafx.scene.image.Image;

public class Evenement {
    private int id;
    private String titre;
    private Date date;
    private String lieu;
    private String image;
    private Timestamp updated_at;
    private Image imageFile;

    public Evenement() {
    }

    public Evenement(int id, String titre, Date date, String lieu, String image, Timestamp updated_at, Image imageFile) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.image = image;
        this.updated_at = updated_at;
        this.imageFile = imageFile;
    }

    public Evenement(int id, String titre, Date date, String lieu, String image, Timestamp updated_at) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.image = image;
        this.updated_at = updated_at;
    }



    public Evenement(String titre, Date date, String lieu, String image, Timestamp updated_at) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.image = image;
        this.updated_at = updated_at;
    }

    public Evenement(String titre, Date date, String lieu, String image, Timestamp updated_at, Image imageFile) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.image = image;
        this.updated_at = updated_at;
        this.imageFile = imageFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Image getImageFile() {
        return imageFile;
    }

    public void setImageFile(Image imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.titre);
        hash = 89 * hash + Objects.hashCode(this.date);
        hash = 89 * hash + Objects.hashCode(this.lieu);
        hash = 89 * hash + Objects.hashCode(this.image);
        hash = 89 * hash + Objects.hashCode(this.updated_at);
        hash = 89 * hash + Objects.hashCode(this.imageFile);
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
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.lieu, other.lieu)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.updated_at, other.updated_at)) {
            return false;
        }
        if (!Objects.equals(this.imageFile, other.imageFile)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", titre=" + titre + ", date=" + date + ", lieu=" + lieu + ", image=" + image + ", updated_at=" + updated_at + ", imageFile=" + imageFile + '}';
    }



}
