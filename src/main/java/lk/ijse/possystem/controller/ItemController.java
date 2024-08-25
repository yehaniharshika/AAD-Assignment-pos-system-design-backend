package lk.ijse.possystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item",loadOnStartup = 2)
public class ItemController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("initializing ItemController with call init method");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            this.connection = pool.getConnection();

        } catch (SQLException |NamingException e){
            logger.error("init method loading failed");
            e.printStackTrace();
        }
    }
}
