package lk.ijse.possystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String  itemCode;
    private String  itemName;
    private String  unitPrice;
    private String  qtyOnHand;
}
