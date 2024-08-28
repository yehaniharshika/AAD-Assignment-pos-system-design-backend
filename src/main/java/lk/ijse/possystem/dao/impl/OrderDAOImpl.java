package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.SQLUtil;
import lk.ijse.possystem.dao.custom.OrderDAO;
import lk.ijse.possystem.dto.OrderDTO;
import lk.ijse.possystem.entity.Customer;
import lk.ijse.possystem.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public static String SAVE_ORDER = "INSERT INTO orders(orderId,orderDate,customerId,total,discount,subTotal,cash,balance) VALUES(?,?,?,?,?,?,?,?)";
    public static String UPDATE_ORDER = "UPDATE orders SET orderDate=?,customerId=?,total=?,discount=?,subTotal=?,cash=?,balance=? WHERE orderId=?";
    public static String DELETE_ORDER = "DELETE FROM orders WHERE orderId = ?";
    public static String GET_ORDERS = "SELECT * FROM orders";
    public static String GET_ORDER_BY_ORDER_ID = "SELECT * FROM orders WHERE orderId=?";
    public static String GET_LAST_ORDER_ID = "SELECT orderId FROM `orders` ORDER BY orderId DESC LIMIT 1";
    /*@Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1,orderDTO.getOrderId());
            ps.setString(2, String.valueOf(orderDTO.getOrderDate()));
            ps.setString(3,orderDTO.getCustomerId());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateOrder(String orderId, OrderDTO orderDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(UPDATE_ORDER);
            ps.setString(1, String.valueOf(orderDTO.getOrderDate()));
            ps.setString(2, orderDTO.getCustomerId());
            ps.setString(3, orderId);

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteOrder(String orderId, Connection connection){
        try {
            var ps  = connection.prepareStatement(DELETE_ORDER);
            ps.setString(1,orderId);

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDTO> getOrder(Connection connection){
        List<OrderDTO> orderList = new ArrayList<>();

        try {
            var ps = connection.prepareStatement(GET_ORDER);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                OrderDTO order = new OrderDTO();
                order.setOrderId(resultSet.getString("orderId"));
                order.setOrderDate(LocalDate.parse(resultSet.getString("orderDate")));
                order.setCustomerId(resultSet.getString("customerId"));

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }*/

    @Override
    public boolean save(Order entity, Connection connection) throws SQLException {
        String sql = SAVE_ORDER;
        return SQLUtil.execute(sql,connection,
                entity.getOrderId(),
                entity.getOrderDate(),
                entity.getCustomerId(),
                entity.getTotal(),
                entity.getDiscount(),
                entity.getSubTotal(),
                entity.getCash(),
                entity.getBalance()
        );
    }

    @Override
    public boolean update(String orderId, Order entity, Connection connection) throws SQLException {
        String sql = UPDATE_ORDER;
        return SQLUtil.execute(
                sql,
                connection,
                entity.getOrderDate(),
                entity.getCustomerId(),
                entity.getTotal(),
                entity.getDiscount(),
                entity.getSubTotal(),
                entity.getCash(),
                entity.getBalance(),
                orderId
        );
    }

    @Override
    public boolean delete(String orderId, Connection connection) throws SQLException {
        String sql = DELETE_ORDER;
        return SQLUtil.execute(
                sql,
                connection,
                orderId
        );
    }

    @Override
    public List<Order> getAll(Connection connection) throws SQLException {
        String sql = GET_ORDERS;
        ResultSet resultSet = SQLUtil.execute(sql,connection);
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(new Order(
                    resultSet.getString("orderId"),
                    resultSet.getString("orderDate"),
                    resultSet.getString("customerId"),
                    resultSet.getDouble("total"),
                    resultSet.getDouble("discount"),
                    resultSet.getDouble("subTotal"),
                    resultSet.getDouble("cash"),
                    resultSet.getDouble("balance")
            ));
        }
        return orderList;
    }

    @Override
    public Order getById(String orderId, Connection connection) throws SQLException {
        String sql = GET_ORDER_BY_ORDER_ID;
        ResultSet resultSet = SQLUtil.execute(sql,connection,orderId);

        if (resultSet.next()){
            return new Order(
                    resultSet.getString("orderId"),
                    resultSet.getString("orderDate"),
                    resultSet.getString("customerId"),
                    resultSet.getDouble("total"),
                    resultSet.getDouble("discount"),
                    resultSet.getDouble("subTotal"),
                    resultSet.getDouble("cash"),
                    resultSet.getDouble("balance")
            );
        }
        return null;
    }

    @Override
    public String generateOrderId(Connection connection) throws SQLException {
        String lastOrderId = null;
        ResultSet resultSet = SQLUtil.execute(GET_LAST_ORDER_ID, connection);
        if (resultSet.next()) {
            lastOrderId = resultSet.getString("orderId");
        }

        if (lastOrderId != null) {
            int lastNumber = Integer.parseInt(lastOrderId.split("-")[1]);
            lastNumber++;
            return "O-" + String.format("%03d", lastNumber);
        }
        return "O-001";
    }
}
