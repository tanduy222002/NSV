package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Annotation.Base64Img;


@Data
@NoArgsConstructor
public class UpdatePartnerDto {
    @Schema( example = "Vinafood")
    private String name;

    @Schema( example = "0794368181")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema( example = "vinafood@gmail.com")
    private String email;

    @Valid
    private AddressDto address;

    @Schema( example = "01234567890")
    @JsonProperty("tax_number")
    private String taxNumber;

    @Schema( example = "+1 (555) 123-4567")
    @JsonProperty("fax_number")
    private String faxNumber;

    @Schema( example = "OCB - 0004100032747007")
    @JsonProperty("bank_acount")
    private String bankAccount;

    @Base64Img
    @Schema(description = "The base64 encoded of image start with prefix: \"data:image/jpeg;base64,\" + code")
    private String avatar;
}
