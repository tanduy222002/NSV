package nsv.com.nsvserver.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class EmployeeDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "tanduy", required = true)
    private String name;
    @Schema( example = "0794368181")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @Schema( example = "tanduy222002@gmail.com")
    private String email;
    @Schema( example = "M")
    private String gender;
    @Schema( example = "[]")
    private List<String> addresses = new ArrayList<String>();

    private String status;

    private List<String> roles = new ArrayList<>();

    public EmployeeDto() {
    }
}
