package lk.ijse.possystem.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystem.bo.CustomerBO;
import lk.ijse.possystem.bo.impl.CustomerBOImpl;
import lk.ijse.possystem.dao.impl.CustomerDAOImpl;
import lk.ijse.possystem.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customer",loadOnStartup = 2)
public class CustomerController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    private CustomerBO customerBO = new CustomerBOImpl();
    @Override
    public void init() throws ServletException {
        logger.info("initializing CustomerController with call init method");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();
            logger.info("connection initialized ",this.connection);
        } catch (SQLException|NamingException e) {
            logger.error("init method failed");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Customer doPost method");
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);


            if (customerBO.saveCustomer(customerDTO,connection)) {
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Failed to save customer");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException e) {
            logger.error("JSON parsing failed", e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Customer doPut method");
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try(var writer = resp.getWriter()){
            var customerId = req.getParameter("customerId");
            Jsonb jsonb = JsonbBuilder.create();


            var updateCustomer = jsonb.fromJson(req.getReader(),CustomerDTO.class);
            logger.debug("customerID: "+customerId);
            logger.debug("updated customer data: "+updateCustomer);

            boolean isUpdated = customerBO.updateCustomer(customerId,updateCustomer,connection);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("customer updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("customer update failed");
            }
        }catch (JsonException | SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("call Customer doDelete method");

        var customerId = req.getParameter("customerId");
        if (customerId == null || customerId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is missing");
            return;
        }

        try (var writer = resp.getWriter()) {
            boolean isDeleted = customerBO.deleteCustomer(customerId, connection);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("Customer deleted successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to delete customer");
            }
        } catch (JsonException | SQLException e) {
            logger.error("Error deleting customer", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Customer doGet method");
        resp.setContentType("application/json");

        try (var writer = resp.getWriter()) {
            var customerDAOImpl = new CustomerDAOImpl();
            Jsonb jsonb = JsonbBuilder.create();

            // Check if there's a customerId parameter
            String customerId = req.getParameter("customerId");
            if (customerId != null && !customerId.isEmpty()) {
                // Search for a specific customer by ID
                CustomerDTO customer = customerBO.getCustomerByCustomerId(customerId, connection);

                if (customer != null) {
                    String customerJson = jsonb.toJson(customer);
                    writer.write(customerJson);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
                }
            } else {
                // Return all customers
                List<CustomerDTO> customers = customerBO.getCustomers(connection);
                String customersJson = jsonb.toJson(customers);
                writer.write(customersJson);
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
            throw new RuntimeException(e);
        }
    }
}
