package lk.ijse.possystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private String qty;
    private String unitPrice;
}
