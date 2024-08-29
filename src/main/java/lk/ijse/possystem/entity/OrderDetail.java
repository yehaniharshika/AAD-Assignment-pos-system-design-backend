package lk.ijse.possystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail {
    private String orderId;
    private String itemCode;
    private String qty;
    private String unitPrice;
}
