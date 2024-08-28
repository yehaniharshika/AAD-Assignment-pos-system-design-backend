package lk.ijse.possystem.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystem.bo.impl.OrderBOImpl;
import lk.ijse.possystem.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order",loadOnStartup = 2)
public class OrderController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    private OrderBOImpl orderBO = new OrderBOImpl();

    @Override
    public void init() throws ServletException {
        logger.info("initializing OrderController with call init method");

        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();
            logger.info("connection initialized ",this.connection);
        } catch (SQLException | NamingException e) {
            logger.error("init method failed");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("OrderController doPost method called");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

            if (orderBO.saveOrder(orderDTO, connection)) {
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Failed to save order");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException e) {
            logger.error("Error saving order", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Order processing failed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("OrderController doGet method called");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            // Logic to generate new order ID
            if ("generateOrderId".equals(req.getParameter("action"))) {
                var newOrderId = orderBO.generateOrderId(connection);
                writer.write(jsonb.toJson(newOrderId));
            }
        } catch (JsonException | SQLException e) {
            logger.error("Error in OrderController doGet", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to process request");
        }
    }

}
