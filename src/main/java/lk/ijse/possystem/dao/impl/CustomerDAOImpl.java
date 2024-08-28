package lk.ijse.possystem.dao.impl;

import lk.ijse.possystem.dao.SQLUtil;
import lk.ijse.possystem.dao.custom.CustomerDAO;
import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer(customerId,name,address,contactNumber,email) VALUES(?,?,?,?,?)";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?,address=?,contactNumber=?,email=? WHERE customerId=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE customerId = ?";
    public static String GET_CUSTOMERS = "SELECT * FROM customer";
    public static String GET_CUSTOMER_BY_CUSTOMER_ID = "SELECT * FROM customer WHERE customerId=?";
    public static String GET_ALL_CUSTOMER_IDS = "SELECT customerId FROM customer";
    public static String GET_LAST_CUSTOMER_ID = "SELECT customerId FROM `customer` ORDER BY customerId DESC LIMIT 1";

    /*@Override
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

    @Override
    public CustomerDTO getCustomerByCustomerId(String customerId, Connection connection){
        CustomerDTO customerDTO = null;
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER_BY_CUSTOMER_ID);
            ps.setString(1,customerId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                customerDTO = new CustomerDTO();
                customerDTO.setCustomerId(resultSet.getString("customerId"));
                customerDTO.setName(resultSet.getString("name"));
                customerDTO.setAddress(resultSet.getString("address"));
                customerDTO.setContactNumber(resultSet.getString("contactNumber"));
                customerDTO.setEmail(resultSet.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerDTO;
    }*/

    @Override
    public boolean save(Customer entity, Connection connection) throws SQLException {
        String sql = SAVE_CUSTOMER;
        return SQLUtil.execute(sql,
                connection,
                entity.getCustomerId(),
                entity.getName(),
                entity.getAddress(),
                entity.getContactNumber(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(String customerId, Customer entity, Connection connection) throws SQLException {
        String sql = UPDATE_CUSTOMER;
        return SQLUtil.execute(sql,
                connection,
                entity.getName(),
                entity.getAddress(),
                entity.getContactNumber(),
                entity.getEmail(),
                customerId
        );
    }

    @Override
    public boolean delete(String customerId, Connection connection) throws SQLException {
        String sql = DELETE_CUSTOMER;
        return SQLUtil.execute(sql,
                connection,
                customerId
        );
    }

    @Override
    public List<Customer> getAll(Connection connection) throws SQLException {
        String sql = GET_CUSTOMERS;
        ResultSet resultSet = SQLUtil.execute(sql,connection);
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()){
            customerList.add(new Customer(
                 resultSet.getString("customerId"),
                 resultSet.getString("name"),
                 resultSet.getString("address"),
                 resultSet.getString("contactNumber"),
                 resultSet.getString("email")
            ));
        }
        return customerList;
    }

    @Override
    public Customer getById(String customerId, Connection connection) throws SQLException {
        String sql = GET_CUSTOMER_BY_CUSTOMER_ID;
        ResultSet resultSet = SQLUtil.execute(sql,connection,customerId);

        if (resultSet.next()){
            return new Customer(
                  resultSet.getString("customerId"),
                  resultSet.getString("name"),
                  resultSet.getString("address"),
                  resultSet.getString("contactNumber"),
                  resultSet.getString("email")
            );
        }
        return null;
    }

    @Override
    public String generateId(Connection connection) throws SQLException {
        String lastCustomerId = null;

        ResultSet resultSet = SQLUtil.execute(GET_LAST_CUSTOMER_ID, connection);
        if (resultSet.next()) {
            lastCustomerId = resultSet.getString("customerId");
        }

        if (lastCustomerId != null) {
            int lastNumber = Integer.parseInt(lastCustomerId.split("-")[1]);
            lastNumber++;
            return "O-" + String.format("%03d", lastNumber);
        }
        return "C-001";
    }

    @Override
    public List<String> getAllCustomerIDs(Connection connection) throws SQLException {
        List<String> customerIDs = new ArrayList<>();
        String sql = GET_ALL_CUSTOMER_IDS;
        ResultSet resultSet = SQLUtil.execute(sql,connection);

        while (resultSet.next()) {
            customerIDs.add(resultSet.getString("customerId"));
        }

        return customerIDs;
    }
}
