package lk.ijse.possystem.bo.impl;

import lk.ijse.possystem.bo.custom.ItemBO;
import lk.ijse.possystem.dao.impl.ItemDAOImpl;
import lk.ijse.possystem.dto.ItemDTO;
import lk.ijse.possystem.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private ItemDAOImpl itemDAO = new ItemDAOImpl();
    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        Item item = new Item(
                itemDTO.getItemCode(),
                itemDTO.getItemName(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand()
        );

        return itemDAO.save(item,connection);
    }

    @Override
    public boolean updateItem(String itemCode, ItemDTO itemDTO, Connection connection) throws SQLException {
        Item item = new Item(
                itemDTO.getItemCode(),
                itemDTO.getItemName(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand()
        );

        return itemDAO.update(itemCode,item,connection);
    }

    @Override
    public boolean deleteItem(String itemCode, Connection connection) throws SQLException {
        return itemDAO.delete(itemCode,connection);
    }

    @Override
    public List<ItemDTO> getItems(Connection connection) throws SQLException {
        List<Item> items = itemDAO.getAll(connection);
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for (Item item:items){
            itemDTOList.add(new ItemDTO(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            ));
        }
        return itemDTOList;
    }

    @Override
    public ItemDTO getItemByItemCode(String itemCode, Connection connection) throws SQLException {
        Item item = itemDAO.getById(itemCode,connection);
        if (item != null){
            return  new ItemDTO(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        }
        return null;
    }

    @Override
    public List<String> loadAllItemCodes(Connection connection) throws SQLException {
        return itemDAO.getAllItemCodes(connection);
    }

    @Override
    public boolean decreaseItemQuantity(String itemCode, int quantity, Connection connection) throws SQLException {
        return itemDAO.decreaseItemQuantity(itemCode,quantity,connection);
    }
}
