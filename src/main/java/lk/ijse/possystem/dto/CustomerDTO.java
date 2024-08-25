package lk.ijse.possystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO implements Serializable {
    private String customerId;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
}
