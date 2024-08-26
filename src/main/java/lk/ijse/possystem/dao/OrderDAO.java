package lk.ijse.possystem.dao;

import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
        boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;
        boolean updateOrder(String orderId,OrderDTO orderDTO,Connection connection) throws SQLException;
        boolean deleteOrder(String orderId,Connection connection) throws SQLException;
        List<OrderDTO> getOrder(Connection connection) throws SQLException;
}
