package lk.ijse.possystem.dao;

import lk.ijse.possystem.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T entity, Connection connection) throws SQLException;

    boolean update(String id, T entity, Connection connection) throws SQLException;

    boolean delete(String id, Connection connection) throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

    T getById(String id, Connection connection) throws SQLException;
    String generateId(Connection connection) throws SQLException;
}
