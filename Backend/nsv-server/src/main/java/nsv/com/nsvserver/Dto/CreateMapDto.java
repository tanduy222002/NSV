package nsv.com.nsvserver.Dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Row;
import nsv.com.nsvserver.Entity.Warehouse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMapDto {

    private String name;
    @Valid
    private List<CreateRowDto> rowDtos;


}
