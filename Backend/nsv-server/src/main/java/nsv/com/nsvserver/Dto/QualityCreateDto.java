package nsv.com.nsvserver.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityCreateDto {
    @NotBlank(message = "quality name must not be blank")
    private String name;
    @NotBlank(message = "quality description must not be blank")
    private String description;
}
