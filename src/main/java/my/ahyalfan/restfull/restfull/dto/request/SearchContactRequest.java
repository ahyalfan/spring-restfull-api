package my.ahyalfan.restfull.restfull.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchContactRequest {
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String email;
    @Size(max = 100)
    private String phone;
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
