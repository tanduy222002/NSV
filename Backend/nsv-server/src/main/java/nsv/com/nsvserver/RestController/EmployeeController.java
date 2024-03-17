package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Service.AuthService;
import nsv.com.nsvserver.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/employees")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get employee list successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),

            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })

    public ResponseEntity<?> getAllEmployee() {
        List<EmployeeDto> employeesDtoList = employeeService.getAllEmployee();
        return ResponseEntity.ok(employeesDtoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get an employee successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })

    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id ) {
        EmployeeDto employeesDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeesDto);
    }


    @Operation(summary = "Update employee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid information"),
            @ApiResponse(responseCode = "404", description = "Employee does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateEmployeeProfile(@PathVariable Integer id, @Valid @RequestBody ProfileDto employeeProfileDto) {
        employeeService.updateEmployeeProfile(id,employeeProfileDto);
        return ResponseEntity.ok("Updated profile successfully");
    }


    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully"),

            @ApiResponse(responseCode = "400", description = "Invalid information"),
            @ApiResponse(responseCode = "404", description = "Employee does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Delete employee successfully");
    }
}
