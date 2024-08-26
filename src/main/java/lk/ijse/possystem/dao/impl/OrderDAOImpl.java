package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.custom.OrderDAO;
import lk.ijse.possystem.dto.OrderDTO;

import java.sql.Connection;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public static String SAVE_ORDER = "INSERT INTO order(orderId,orderDate,customerId) VALUES(?,?,?)";
    public static String UPDATE_ORDER = "UPDATE order SET orderDate=?,customerId=? WHERE orderId=?";
    public static String DELETE_ORDER = "DELETE FROM order WHERE orderId = ?";
    public static String GET_ORDER = "SELECT * FROM order";
    @Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection){
        return false;
    }

    @Override
    public boolean updateOrder(String orderId, OrderDTO orderDTO, Connection connection){
        return false;
    }

    @Override
    public boolean deleteOrder(String orderId, Connection connection){
        return false;
    }

    @Override
    public List<OrderDTO> getOrder(Connection connection){
        return null;
    }
}
