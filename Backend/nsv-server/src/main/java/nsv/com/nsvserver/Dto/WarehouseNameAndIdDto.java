package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseNameAndIdDto {
    private Integer id;
    private String name;
}