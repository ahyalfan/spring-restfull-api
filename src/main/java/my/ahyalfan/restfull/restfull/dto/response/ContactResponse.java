package my.ahyalfan.restfull.restfull.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
