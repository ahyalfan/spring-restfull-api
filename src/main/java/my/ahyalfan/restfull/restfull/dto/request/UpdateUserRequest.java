package my.ahyalfan.restfull.restfull.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String password;
}
