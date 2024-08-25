package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.ItemDAO;
import lk.ijse.possystem.dto.ItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    public static String SAVE_ITEM = "INSERT INTO item(itemCode,itemName,unitPrice,qtyOnHand) VALUES(?,?,?,?)";
    public static String UPDATE_ITEM  = "UPDATE item SET itemName=?,unitPrice=?,qtyOnHand=? WHERE itemCode=?";
    public static String DELETE_ITEM  = "DELETE FROM item WHERE itemCode = ?";
    public static String GET_ITEMS = "SELECT * FROM item";
    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, itemDTO.getItemCode());
            ps.setString(2, itemDTO.getItemName());
            ps.setString(3, itemDTO.getUnitPrice());
            ps.setString(4, itemDTO.getQtyOnHand());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(String itemCode, ItemDTO itemDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1,itemDTO.getItemName());
            ps.setString(2,itemDTO.getUnitPrice());
            ps.setString(3,itemDTO.getQtyOnHand());
            ps.setString(4,itemCode);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String itemCode, Connection connection){
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1,itemCode);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemDTO> getItems(Connection connection){
        List<ItemDTO> itemList = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_ITEMS);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                ItemDTO item = new ItemDTO();
                item.setItemCode(resultSet.getString("itemCode"));
                item.setItemName(resultSet.getString("itemName"));
                item.setUnitPrice(resultSet.getString("unitPrice"));
                item.setQtyOnHand(resultSet.getString("qtyOnHand"));
                itemList.add(item);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }
}
