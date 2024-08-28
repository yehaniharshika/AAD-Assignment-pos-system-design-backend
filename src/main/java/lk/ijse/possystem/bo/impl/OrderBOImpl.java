package lk.ijse.possystem.bo.impl;

import lk.ijse.possystem.bo.custom.OrderBO;
import lk.ijse.possystem.dao.impl.CustomerDAOImpl;
import lk.ijse.possystem.dao.impl.ItemDAOImpl;
import lk.ijse.possystem.dao.impl.OrderDAOImpl;
import lk.ijse.possystem.dto.ItemDTO;
import lk.ijse.possystem.dto.OrderDTO;
import lk.ijse.possystem.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private OrderDAOImpl orderDAO = new OrderDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private ItemDAOImpl itemDAO = new ItemDAOImpl();
    @Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException {
        Order order = new Order(
                orderDTO.getOrderId(),
                orderDTO.getOrderDate(),
                orderDTO.getCustomerId(),
                orderDTO.getTotal(),
                orderDTO.getDiscount(),
                orderDTO.getSubTotal(),
                orderDTO.getCash(),
                orderDTO.getBalance()
        );
        return orderDAO.save(order,connection);
    }

    @Override
    public boolean updateOrder(String orderId, OrderDTO orderDTO, Connection connection) throws SQLException {
        Order order = new Order(
                orderDTO.getOrderId(),
                orderDTO.getOrderDate(),
                orderDTO.getCustomerId(),
                orderDTO.getTotal(),
                orderDTO.getDiscount(),
                orderDTO.getSubTotal(),
                orderDTO.getCash(),
                orderDTO.getBalance()
        );
        return orderDAO.update(orderId,order,connection);
    }

    @Override
    public boolean deleteOrder(String orderId, Connection connection) throws SQLException {
        return orderDAO.delete(orderId,connection);
    }

    @Override
    public List<OrderDTO> getOrder(Connection connection) throws SQLException {
        List<Order> orders = orderDAO.getAll(connection);
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order:orders){
            orderDTOList.add(new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getCustomerId(),
                    order.getTotal(),
                    order.getDiscount(),
                    order.getSubTotal(),
                    order.getCash(),
                    order.getBalance()
            ));
        }
        return orderDTOList;
    }

    @Override
    public OrderDTO getOrderByOrderId(String orderId, Connection connection) throws SQLException {
        Order order = orderDAO.getById(orderId,connection);
        if (order != null){
            return new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getCustomerId(),
                    order.getTotal(),
                    order.getDiscount(),
                    order.getSubTotal(),
                    order.getCash(),
                    order.getBalance()
            );
        }
        return null;
    }

    @Override
    public String generateOrderId(Connection connection) throws SQLException {
        return orderDAO.generateId(connection);
    }

    @Override
    public List<String> loadAllCustomerIDs(Connection connection) throws SQLException {
        return customerDAO.getAllCustomerIDs(connection);
    }

    @Override
    public List<String> loadAllItemCodes(Connection connection) throws SQLException {
        return itemDAO.getAllItemCodes(connection);
    }
}
