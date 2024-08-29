package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.custom.OrderDetailDAO;
import lk.ijse.possystem.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(OrderDetail entity, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean update(String id, OrderDetail entity, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public OrderDetail getById(String id, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public String generateId(Connection connection) throws SQLException {
        return null;
    }
}
