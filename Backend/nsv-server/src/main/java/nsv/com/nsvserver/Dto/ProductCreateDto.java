package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nsv.com.nsvserver.Anotation.Base64Img;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Type;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "Sau Rieng", required = true)
    private String name;

    @NotBlank(message = "variety is mandatory")
    @Schema( example = "Trai Cay", required = true)
    private String variety;
    @Base64Img
    @Schema(description = "The base64 encoded of image start with prefix: \"data:image/jpeg;base64,\" + code")
    private String image;
}
