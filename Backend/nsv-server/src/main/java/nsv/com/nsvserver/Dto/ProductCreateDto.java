package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Entity.Product;


@Getter
@Setter
public class ProductCreateDto {
    @NotBlank(message = "name is mandatory")
    @Schema( example = "Sau Rieng", required = true)
    private String name;
    @NotBlank(message = "variety is mandatory")
    @Schema( example = "Trai Cay", required = true)
    private String variety;
    @Schema( example = "https://media.istockphoto.com/id/1329896565/vi/anh/tr%C3%A1i-s%E1%BA%A7u-ri%C3%AAng-%C4%91%C6%B0%E1%BB%A3c-l%C3%A0m-tr%E1%BA%AFng.jpg?s=612x612&w=0&k=20&c=eJ92Fdfj8tJS0DFCcpF7peZ99WPkfavsfc6lO5Xe_xc=")
    private String image;

}
