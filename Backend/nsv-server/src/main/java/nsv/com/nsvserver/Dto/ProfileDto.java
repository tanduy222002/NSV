package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfileDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "tanduy", required = true)
    private String name;
    @Schema( example = "0794368181")
    private String phoneNumber;
    @Schema( example = "tanduy222002@gmail.com")
    private String email;
    @Schema( example = "M")
    private String gender;
    @Schema( example = "[]")
    private List<String> addresses = new ArrayList<String>();

    public ProfileDto() {
    }
}
