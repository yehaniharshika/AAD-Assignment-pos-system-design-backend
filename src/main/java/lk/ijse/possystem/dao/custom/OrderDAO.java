package lk.ijse.possystem.dao.custom;

import lk.ijse.possystem.dao.CrudDAO;
import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.dto.OrderDTO;
import lk.ijse.possystem.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
        /*boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;
        boolean updateOrder(String orderId,OrderDTO orderDTO,Connection connection) throws SQLException;
        boolean deleteOrder(String orderId,Connection connection) throws SQLException;
        List<OrderDTO> getOrder(Connection connection) throws SQLException;*/

}
