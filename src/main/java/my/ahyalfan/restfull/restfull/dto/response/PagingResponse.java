package my.ahyalfan.restfull.restfull.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private int currentPage;
    private int totalPages;
    private int size;
}
