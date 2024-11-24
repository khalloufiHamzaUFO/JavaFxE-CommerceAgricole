package repositories;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IService<T>{
    public void ajouter(T p);
    public void supprimer(int id) throws SQLException;
    public void modifier(T p);
    public ObservableList<T> getAll();
    public T getById(int p);


}
