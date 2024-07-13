package my.ahyalfan.restfull.restfull.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;

    private String street;

    private String city;

    private String province;

    private String country;

    private String postalCode;
}
