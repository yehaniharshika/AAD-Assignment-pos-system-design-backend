package lk.ijse.possystem.controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystem.bo.custom.CustomerBO;
import lk.ijse.possystem.bo.impl.CustomerBOImpl;
import lk.ijse.possystem.bo.impl.ItemBOImpl;
import lk.ijse.possystem.dao.impl.ItemDAOImpl;
import lk.ijse.possystem.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item",loadOnStartup = 2)
public class ItemController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    Connection connection;
    private ItemBOImpl itemBO = new ItemBOImpl();

    @Override
    public void init() throws ServletException {
        logger.info("initializing ItemController with call init method");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();

        } catch (SQLException |NamingException e){
            logger.error("init method loading failed");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Item doPost method");

        try(var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);

            //var itemDAOImpl = new ItemDAOImpl();
            if (itemBO.saveItem(itemDTO,connection)){
                writer.write("Item saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Failed to save item");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Item doPut method");
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try(var writer = resp.getWriter()){
            var itemCode = req.getParameter("itemCode");
            Jsonb jsonb = JsonbBuilder.create();

            var updateItem = jsonb.fromJson(req.getReader(),ItemDTO.class);
            logger.debug("item code: "+itemCode);
            logger.debug("update item data: "+updateItem);
            boolean isUpdated = itemBO.updateItem(itemCode,updateItem,connection);

            if (isUpdated){
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("Item updated successfully");
            }else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("Item update failed");
            }
        }catch (JsonException | SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call item doDelete method");

        var itemCode = req.getParameter("itemCode");
        logger.debug("delete item code: "+itemCode);
        if (itemCode == null || itemCode.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is missing");
            return;
        }
        try(var writer = resp.getWriter() ){
            var itemDAOImpl = new ItemDAOImpl();
            boolean isDeleted = itemBO.deleteItem(itemCode,connection);

            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("item deleted successfully");
            }else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("failed to delete item ");
            }
        }catch (JsonException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("call Item doGet method");
        resp.setContentType("application/json");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            String action = req.getParameter("action");
            String itemCode = req.getParameter("itemCode");

            if ("generateItemCode".equals(action)) {
                // Logic to generate new item code
                var newItemCode = itemBO.generateNextItemCode(connection);
                logger.debug("Generated Item Code: " + newItemCode);
                writer.write(jsonb.toJson(newItemCode));

            } else if (itemCode != null && !itemCode.isEmpty()) {
                ItemDTO item = itemBO.getItemByItemCode(itemCode, connection);
                if (item != null) {
                    String itemJson = jsonb.toJson(item);
                    writer.write(itemJson);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
                }
            } else {
                List<ItemDTO> items = itemBO.getItems(connection);
                String itemsJson = jsonb.toJson(items);
                writer.write(itemsJson);
            }

        } catch (JsonException | SQLException e) {
            logger.error("Error processing request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to process request");
        }
    }

}
