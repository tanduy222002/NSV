package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapInWareHouseDto {
    private String id;
    @JsonProperty("map_name")
    private String mapName;
    @Valid
    private List<RowInMapDto> rows;


}
