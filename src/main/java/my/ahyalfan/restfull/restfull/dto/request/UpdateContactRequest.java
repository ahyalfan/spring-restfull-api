package my.ahyalfan.restfull.restfull.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactRequest {
    @Min(0)
    @JsonIgnore
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @Size(max = 100)
    private String lastName;
    @Email
    @Size(max = 100)
    private String email;
    @Size(max = 100)
    private String phone;
}
