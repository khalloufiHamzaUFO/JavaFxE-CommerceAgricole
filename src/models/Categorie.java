package models;

import lombok.*;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
    private int id;
    private String label;
    private String icon;
    private List<Produit> produits;

//    public Categorie() {
//        produits = new ArrayList<>();
//    }

    public Categorie(String label,String icon) {
        this.label = label;
        this.icon = icon;
        produits = new ArrayList<>();
    }

    public Categorie(int id, String label,String icon) {
        this.id = id;
        this.label = label;
        this.icon = icon;
        produits = new ArrayList<>();
    }


    public List<Produit> getProduits() {
        return produits;
    }
    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
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
        final Categorie other = (Categorie) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
