package lk.ijse.possystem.bo.impl;

import lk.ijse.possystem.bo.custom.CustomerBO;
import lk.ijse.possystem.dao.custom.CustomerDAO;
import lk.ijse.possystem.dao.impl.CustomerDAOImpl;
import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException {
        Customer customer = new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContactNumber(),
                customerDTO.getEmail()
        );
        return customerDAO.save(customer,connection);
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO customerDTO, Connection connection) throws SQLException {
        Customer customer = new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContactNumber(),
                customerDTO.getEmail()
        );
        return customerDAO.update(customerId,customer,connection);
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) throws SQLException {
        return customerDAO.delete(customerId,connection);
    }

    @Override
    public List<CustomerDTO> getCustomers(Connection connection) throws SQLException {
        List<Customer> customers = customerDAO.getAll(connection);
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Customer customer : customers){
            customerDTOList.add(new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContactNumber(),
                    customer.getEmail()
            ));
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO getCustomerByCustomerId(String customerId, Connection connection) throws SQLException {
        Customer customer = customerDAO.getById(customerId,connection);
        if (customer != null){
            return new CustomerDTO(
                 customer.getCustomerId(),
                 customer.getName(),
                 customer.getAddress(),
                 customer.getContactNumber(),
                 customer.getEmail()
            );
        }
        return null;
    }
}
