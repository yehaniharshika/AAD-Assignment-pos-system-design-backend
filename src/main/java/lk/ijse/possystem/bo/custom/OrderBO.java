package lk.ijse.possystem.bo.custom;

import lk.ijse.possystem.dto.ItemDTO;
import lk.ijse.possystem.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO {
    boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;
    boolean updateOrder(String orderId,OrderDTO orderDTO,Connection connection) throws SQLException;
    boolean deleteOrder(String orderId,Connection connection) throws SQLException;
    List<OrderDTO> getOrder(Connection connection) throws SQLException;
    OrderDTO getOrderByOrderId(String orderId, Connection connection) throws SQLException;
    public String generateOrderId(Connection connection) throws SQLException;
    List<String> loadAllCustomerIDs(Connection connection) throws SQLException;
    List<String> loadAllItemCodes(Connection connection) throws SQLException;
}
