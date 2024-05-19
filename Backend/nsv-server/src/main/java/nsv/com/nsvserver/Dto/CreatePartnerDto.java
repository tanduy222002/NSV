package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class CreatePartnerDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "Vinafood", required = true)
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
}
