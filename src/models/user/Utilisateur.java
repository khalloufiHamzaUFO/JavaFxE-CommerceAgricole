package models.user;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private int cin;
    private String telephone;
    private String email;
    private String password;
    private String roles;
    private byte isVerified ;
    public Utilisateur() {
    }


    public Utilisateur(String roles) {
        this.roles = roles;
    }
    public Utilisateur(String nom, String prenom, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }
    

    public Utilisateur(String nom, String prenom, int cin, String telephone, String email, String password, String roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Utilisateur(String nom, String prenom, int cin, String telephone, String email, String password, String roles, byte isVerified) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isVerified = isVerified;
    }


    public Utilisateur(int id, String nom, String prenom, int cin, String telephone, String email, String password, String roles, byte isVerified) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isVerified = isVerified;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(byte isVerified) {
        this.isVerified = isVerified;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", telephone=" + telephone + ", email=" + email + ", password=" + password + ", isVerified=" + isVerified + ", roles=" + roles + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
}
