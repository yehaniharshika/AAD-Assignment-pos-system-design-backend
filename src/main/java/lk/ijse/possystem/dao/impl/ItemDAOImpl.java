package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.SQLUtil;
import lk.ijse.possystem.dao.custom.ItemDAO;
import lk.ijse.possystem.dto.ItemDTO;
import lk.ijse.possystem.entity.Item;

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
    public static String GET_ITEM_BY_ITEM_CODE = "SELECT * FROM item WHERE itemCode=?";
    public static String GET_ALL_ITEM_CODES = "SELECT itemCode FROM item";
    public static String GET_LAST_ITEM_CODE = "SELECT itemCode FROM `item` ORDER BY itemCode DESC LIMIT 1";

    /*@Override
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

    @Override
    public ItemDTO getItemByItemCode(String itemCode, Connection connection){
        ItemDTO itemDTO = null;

        try {
            var ps = connection.prepareStatement(GET_ITEM_BY_ITEM_CODE);
            ps.setString(1,itemCode);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                itemDTO = new ItemDTO();
                itemDTO.setItemCode(resultSet.getString("itemCode"));
                itemDTO.setItemName(resultSet.getString("itemName"));
                itemDTO.setUnitPrice(resultSet.getString("unitPrice"));
                itemDTO.setQtyOnHand(resultSet.getString("qtyOnHand"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDTO;
    }*/

    @Override
    public boolean save(Item entity, Connection connection) throws SQLException {
        String sql = SAVE_ITEM;
        return SQLUtil.execute(sql,
                connection,
                entity.getItemCode(),
                entity.getItemName(),
                entity.getUnitPrice(),
                entity.getQtyOnHand()
        );
    }

    @Override
    public boolean update(String itemCode, Item entity, Connection connection) throws SQLException {
        String sql = UPDATE_ITEM;
        return SQLUtil.execute(sql,
                connection,
                entity.getItemName(),
                entity.getUnitPrice(),
                entity.getQtyOnHand(),
                itemCode
        );
    }

    @Override
    public boolean delete(String itemCode, Connection connection) throws SQLException {
        String sql = DELETE_ITEM;
        return SQLUtil.execute(sql,
                connection,
                itemCode
        );
    }

    @Override
    public List<Item> getAll(Connection connection) throws SQLException {
        String sql = GET_ITEMS;
        ResultSet resultSet = SQLUtil.execute(sql,connection);
        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()){
            itemList.add(new Item(
                    resultSet.getString("itemCode"),
                    resultSet.getString("itemName"),
                    resultSet.getString("unitPrice"),
                    resultSet.getString("qtyOnHand")
            ));
        }
        return itemList;
    }

    @Override
    public Item getById(String itemCode, Connection connection) throws SQLException {
        String sql = GET_ITEM_BY_ITEM_CODE;
        ResultSet resultSet = SQLUtil.execute(sql,connection,itemCode);
        if (resultSet.next()){
            return new Item(
                    resultSet.getString("itemCode"),
                    resultSet.getString("itemName"),
                    resultSet.getString("unitPrice"),
                    resultSet.getString("qtyOnHand")
            );
        }
        return null;
    }

    @Override
    public String generateId(Connection connection) throws SQLException {
        String lastItemCode = null;

        ResultSet resultSet = SQLUtil.execute(GET_LAST_ITEM_CODE, connection);
        if (resultSet.next()) {
            lastItemCode = resultSet.getString("itemCode");
        }

        if (lastItemCode != null) {
            int lastNumber = Integer.parseInt(lastItemCode.split("-")[1]);
            lastNumber++;
            return "I-" + String.format("%03d", lastNumber);
        }
        return "I-001";
    }

    @Override
    public List<String> getAllItemCodes(Connection connection) throws SQLException {
        List<String> itemCodes = new ArrayList<>();
        String sql = GET_ALL_ITEM_CODES;
        ResultSet resultSet = SQLUtil.execute(sql,connection);

        while (resultSet.next()) {
            itemCodes.add(resultSet.getString("itemCode"));
        }

        return itemCodes;
    }

    @Override
    public boolean decreaseItemQuantity(String itemCode, int quantity, Connection connection) throws SQLException {
        String selectQuery = "SELECT qtyOnHand FROM item WHERE itemCode = ?";
        ResultSet resultSet = SQLUtil.execute(selectQuery, connection, itemCode);

        int currentQty;
        if (resultSet.next()) {
            currentQty = resultSet.getInt("qtyOnHand");
        } else {
            throw new SQLException("Item not found");
        }

        // Step 2: Check if there's enough quantity to decrease
        if (currentQty < quantity) {
            throw new SQLException("Not enough quantity available");
        }

        // Step 3: Update the quantity on hand using executeUpdate
        String updateQuery = "UPDATE item SET qtyOnHand = ? WHERE itemCode = ?";
        int rowsAffected = SQLUtil.execute(updateQuery, connection, currentQty - quantity, itemCode);

        return rowsAffected > 0;
    }

}
