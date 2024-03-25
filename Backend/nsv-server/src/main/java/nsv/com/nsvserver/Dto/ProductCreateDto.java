package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Type;


@Getter
@Setter
public class ProductCreateDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "Sau Rieng", required = true)
    private String name;
    @NotBlank(message = "variety is mandatory")
    @Schema( example = "Trai Cay", required = true)
    private String variety;
    @NotBlank(message = "type is mandatory")
    @Schema(required = true)
    private Type type;
    @Schema(description = "The base64 encoded of image start with prefix: \"data:image/jpeg;base64,\" + code")
    private String image;

}
