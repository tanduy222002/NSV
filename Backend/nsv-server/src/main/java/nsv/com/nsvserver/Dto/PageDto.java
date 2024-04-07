package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    @JsonProperty("total_page")
    private Object totalPage;
    @JsonProperty("total_element")
    private Object totalElement;
    private Object page;
    private List<?> content;
}
