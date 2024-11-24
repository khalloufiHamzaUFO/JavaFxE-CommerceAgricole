
package models.user;


public class Admin extends Utilisateur {

    public Admin() {
    }

    public Admin(String nom, String prenom, int cin, String telephone, String email, String password, String roles) {
        super(nom, prenom, cin, telephone, email, password, roles);
    }

    public Admin(String nom, String prenom, int cin, String telephone, String email, String password, String roles, byte isVerified) {
        super(nom, prenom, cin, telephone, email, password, roles, isVerified);
    }

    public Admin(int id, String nom, String prenom, int cin, String telephone, String email, String password, String roles, byte isVerified) {
        super(id, nom, prenom, cin, telephone, email, password, roles, isVerified);
    }

   
    
}
