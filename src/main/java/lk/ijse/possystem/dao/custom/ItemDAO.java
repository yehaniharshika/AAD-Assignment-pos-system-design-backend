package lk.ijse.possystem.dao.custom;

import lk.ijse.possystem.dao.CrudDAO;
import lk.ijse.possystem.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException;
    boolean updateItem(String itemCode,ItemDTO itemDTO,Connection connection) throws SQLException;
    boolean deleteItem(String itemCode,Connection connection) throws SQLException;
    List<ItemDTO> getItems(Connection connection) throws SQLException;
    ItemDTO getItemByItemCode(String itemCode, Connection connection) throws SQLException;
}
