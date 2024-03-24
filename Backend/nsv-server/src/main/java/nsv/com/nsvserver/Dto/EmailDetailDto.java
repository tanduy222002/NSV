package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetailDto {
    private String recipient;
    @JsonProperty("msg_body")
    private String msgBody;
    private String subject;
    private String attachment;
}
