package lk.ijse.possystem.bo.custom;

import lk.ijse.possystem.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException;
    boolean updateItem(String itemCode,ItemDTO itemDTO,Connection connection) throws SQLException;
    boolean deleteItem(String itemCode,Connection connection) throws SQLException;
    List<ItemDTO> getItems(Connection connection) throws SQLException;
    ItemDTO getItemByItemCode(String itemCode, Connection connection) throws SQLException;
    List<String> loadAllItemCodes(Connection connection) throws SQLException;
    boolean decreaseItemQuantity(String itemCode, int quantity, Connection connection) throws SQLException;
    String generateNextItemCode(Connection connection) throws SQLException;
}
