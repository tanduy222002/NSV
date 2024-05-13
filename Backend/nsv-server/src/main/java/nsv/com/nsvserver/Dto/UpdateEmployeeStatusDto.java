package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Util.EmployeeStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeStatusDto {
    @NotNull
    @Schema(description = "Employee status must be: ACTIVE/SUSPENDED")
    private EmployeeStatus status;
}
