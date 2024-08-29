package lk.ijse.possystem.bo;

import lk.ijse.possystem.bo.impl.CustomerBOImpl;
import lk.ijse.possystem.bo.impl.ItemBOImpl;
import lk.ijse.possystem.bo.impl.OrderBOImpl;
import lk.ijse.possystem.bo.impl.OrderDetailBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER_BO,
        PRODUCT_BO,
        ORDERS_BO,
        ORDER_DETAILS_BO
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes) {
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            case PRODUCT_BO:
                return (T) new ItemBOImpl();
            case ORDERS_BO:
                return (T) new OrderBOImpl();
            case ORDER_DETAILS_BO:
                return (T) new OrderDetailBOImpl();
            default:
                return null;
        }
    }
}
