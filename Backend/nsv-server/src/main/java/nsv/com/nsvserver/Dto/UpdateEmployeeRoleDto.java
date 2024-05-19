package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Util.EmployeeRoles;
import nsv.com.nsvserver.Util.EmployeeStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRoleDto {
    @NotNull
    @Schema(description = "Employee role must be: ROLE_EMPLOYEE/ROLE_MANAGER/ROLE_ADMIN")
    private EmployeeRoles role;
}
