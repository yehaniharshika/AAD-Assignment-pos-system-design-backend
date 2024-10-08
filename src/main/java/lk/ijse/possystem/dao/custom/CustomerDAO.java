package lk.ijse.possystem.dao.custom;

import lk.ijse.possystem.dao.CrudDAO;
import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer>{
    //boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException;
    //boolean updateCustomer(String customerId,CustomerDTO customerDTO,Connection connection) throws SQLException;
    //boolean deleteCustomer(String customerId,Connection connection) throws SQLException;
    //List<CustomerDTO> getCustomers(Connection connection) throws SQLException;
    //CustomerDTO getCustomerByCustomerId(String customerId,Connection connection) throws SQLException;*/
    List<String> getAllCustomerIDs(Connection connection) throws SQLException;
}
