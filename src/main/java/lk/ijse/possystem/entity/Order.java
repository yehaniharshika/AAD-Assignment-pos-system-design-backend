package lk.ijse.possystem.entity;

import lk.ijse.possystem.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String orderId;
    private String orderDate;
    private String customerId;
    private double total;
    private double discount;
    private double subTotal;
    private double cash;
    private double balance;
}
