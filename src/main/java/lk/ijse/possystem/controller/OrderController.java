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
import lk.ijse.possystem.dto.CustomerDTO;
import lk.ijse.possystem.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        resp.setContentType("application/json");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            String action = req.getParameter("action");
            logger.debug("Action parameter: " + action);

            if ("generateOrderId".equals(action)) {
                // Logic to generate new order ID
                var newOrderId = orderBO.generateOrderId(connection);
                logger.debug("New Order ID: " + newOrderId);
                writer.write(jsonb.toJson(newOrderId));

            } else {
                // Handle other actions, such as fetching order details
                String orderId = req.getParameter("orderId");
                logger.debug("Order ID parameter: " + orderId);

                if (orderId != null && !orderId.isEmpty()) {
                    OrderDTO order = orderBO.getOrderByOrderId(orderId, connection);
                    if (order != null) {
                        writer.write(jsonb.toJson(order));
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                    }
                } else {
                    List<OrderDTO> orders = orderBO.getOrder(connection);
                    writer.write(jsonb.toJson(orders));
                }
            }
        } catch (JsonException | SQLException e) {
            logger.error("Error in OrderController doGet", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to process request");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Order doPut method");

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try(var writer = resp.getWriter()){
            var orderId = req.getParameter("orderId");

            Jsonb jsonb = JsonbBuilder.create();


            var updateOrder = jsonb.fromJson(req.getReader(), OrderDTO.class);
            logger.debug("order ID: "+ orderId);
            logger.debug("updated order data: "+updateOrder);

            boolean isUpdated = orderBO.updateOrder(orderId,updateOrder,connection);

            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("order updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("order update failed");
            }
        }catch (JsonException | SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("call order doDelete method");

        var orderId = req.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "order ID is missing");
            return;
        }

        try (var writer = resp.getWriter()) {
            boolean isDeleted = orderBO.deleteOrder(orderId, connection);

            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("order deleted successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to delete order");
            }
        } catch (JsonException | SQLException e) {
            logger.error("Error deleting order", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
