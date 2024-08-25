package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.CustomerDAO;
import lk.ijse.possystem.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer(customerId,name,address,contactNumber,email) VALUES(?,?,?,?,?)";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?,address=?,contactNumber=?,email=? WHERE customerId=?";
    public static String DELETE_CUSTOMER = "";
    public static String GET_CUSTOMERS = "SELECT * FROM customer";

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1,customerDTO.getCustomerId());
            ps.setString(2,customerDTO.getName());
            ps.setString(3,customerDTO.getAddress());
            ps.setString(4,customerDTO.getContactNumber());
            ps.setString(5,customerDTO.getEmail());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO updateCustomer, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public List<CustomerDTO> getCustomers(Connection connection) throws SQLException {
        return null;
    }
}
