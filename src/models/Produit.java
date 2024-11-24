package models;

public class Produit {
    private int id;
    private String nom;
    private float prix;
    private String description;
    private boolean status;
    private String image;

    private Categorie categorie;

    public Produit(int id, String nom, float prix, String description, boolean status, String image, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.status = status;
        this.image = image;
        this.categorie = categorie;
    }

    public Produit() {
    }

    public Produit(String nom, float prix, String description, boolean status, String image, Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.status = status;
        this.image = image;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom() {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", image='" + image + '\'' +
                ", categorie=" + categorie.getLabel() +
                '}';
    }
}
