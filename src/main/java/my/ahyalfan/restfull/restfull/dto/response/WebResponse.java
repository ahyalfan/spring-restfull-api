package my.ahyalfan.restfull.restfull.dto.response;

//ini kita buat sebagai json untama si semua response
// yg mana ada status, data, errors
//yg mana data bisa berubah ubah, misalnya bisa cuma string, bisa berupa object,dll
//maka kita buat generic class saja

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WebResponse<T> {
    private HttpStatus status;
    private T data;
    private String errors;
    private PagingResponse paging;
}
