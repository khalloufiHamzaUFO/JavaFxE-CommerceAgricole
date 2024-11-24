package repositories;

import models.user.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface IIService<user> {
    public void ajouter(user u) throws SQLException;
    public void supprimer(Utilisateur user) throws SQLException;
    public void modifier(user u) throws SQLException;
    public List<user> getAll() throws SQLException;
    public user getOneById(int id) throws SQLException;
    public boolean  verifierEmail(String email);
}
