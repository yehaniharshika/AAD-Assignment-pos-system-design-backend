package lk.ijse.possystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private List<OrderDetailDTO> orderDetails;
}
