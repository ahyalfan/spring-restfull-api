package my.ahyalfan.restfull.restfull.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
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
public class UpdateAddressRequest {
    @JsonIgnore
    private Long idAddress;
    @JsonIgnore
    private Long contactId;
    @Size(max = 200)
    private String street;
    @Size(max = 100)
    private String city;
    @Size(max = 100)
    private String province;
    @Size(max = 100)
    @NotBlank
    private String country;
    @Size(max = 100)
    private String postalCode;
}
