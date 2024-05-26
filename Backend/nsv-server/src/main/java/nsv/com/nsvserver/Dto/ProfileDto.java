package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Annotation.Base64Img;
import nsv.com.nsvserver.Annotation.ValidEmail;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfileDto {

//    @NotBlank(message = "name is mandatory")
    @Schema( example = "tanduy" )
    private String name;
    @Schema( example = "0794368181")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @Schema( example = "tanduy222002@gmail.com")
    private String email;
    @Schema( example = "M")
    private String gender;

    @Base64Img
    @Schema(description = "The base64 encoded of image start with prefix: \"data:image/jpeg;base64,\" + code")
    private String avatar;

    @Valid
    private AddressDto addresses;

    public ProfileDto() {
    }
}
