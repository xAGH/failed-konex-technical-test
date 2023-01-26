package konex.innovation.medicine_administration.dto.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private Boolean status;
    private String message = null;
    private Object data = null;

    public ResponseDto(Boolean status) {
        this.status = status;
    }

}
