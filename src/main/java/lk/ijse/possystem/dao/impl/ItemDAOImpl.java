package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.ItemDAO;
import lk.ijse.possystem.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean updateItem(String itemCode, ItemDTO itemDTO, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public List<ItemDTO> getItems(Connection connection) throws SQLException {
        return null;
    }
}
