package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.CustomerDAO;
import lk.ijse.possystem.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer(customerId,name,address,contactNumber,email) VALUES(?,?,?,?,?)";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?,address=?,contactNumber=?,email=? WHERE customerId=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE customerId = ?";
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
    public boolean updateCustomer(String customerId, CustomerDTO customerDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1,customerDTO.getName());
            ps.setString(2,customerDTO.getAddress());
            ps.setString(3,customerDTO.getContactNumber());
            ps.setString(4,customerDTO.getEmail());
            ps.setString(5,customerId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection){
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1,customerId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CustomerDTO> getCustomers(Connection connection){
        List<CustomerDTO> customerList = new ArrayList<>();

        try {
            var ps = connection.prepareStatement(GET_CUSTOMERS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                CustomerDTO customer = new CustomerDTO();
                customer.setCustomerId(resultSet.getString("customerId"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setContactNumber(resultSet.getString("contactNumber"));
                customer.setEmail(resultSet.getString("email"));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }
}
