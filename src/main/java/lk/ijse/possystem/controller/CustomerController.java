package lk.ijse.possystem.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@WebServlet(urlPatterns = "/customer",loadOnStartup = 2)
public class CustomerController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();
            /*logger.info("connection initialized ",this.connection);*/
        } catch (SQLException|NamingException e) {
            logger.error("init method failed");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call doPost method");
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            var saveCustomerData = new CustomerDAOImpl();
            if (saveCustomerData.saveCustomer(customerDTO, connection)) {
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Failed to save customer");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            logger.error("JSON parsing failed", e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
