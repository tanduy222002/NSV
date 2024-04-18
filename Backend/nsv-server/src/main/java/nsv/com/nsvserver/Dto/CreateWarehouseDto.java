package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWarehouseDto {

    @NotBlank(message = "name is mandatory")
    @Schema(name = "Kho 1")
    private String name;

    @NotBlank(message = "type is mandatory")
    @Schema(name = "Kho lạnh")
    private String type;

    @NotNull(message = "mapId is mandatory")
    private Integer mapId;

    @Valid
    private AddressDto address;
}
