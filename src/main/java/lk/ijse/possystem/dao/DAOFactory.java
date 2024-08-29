package lk.ijse.possystem.dao;

import lk.ijse.possystem.dao.impl.CustomerDAOImpl;
import lk.ijse.possystem.dao.impl.ItemDAOImpl;
import lk.ijse.possystem.dao.impl.OrderDAOImpl;
import lk.ijse.possystem.dao.impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public  enum DAOTypes{
        CUSTOMER_DAO,
        ITEM_DAO,
        ORDER_DAO,
        ORDER_DETAIL_DAO
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER_DAO:
                return (T) new CustomerDAOImpl();
            case ITEM_DAO:
                return (T) new ItemDAOImpl();
            case ORDER_DAO:
                return (T) new OrderDAOImpl();
            case ORDER_DETAIL_DAO:
                return (T) new OrderDetailDAOImpl();
            default:
                return null;

        }
    }
}
