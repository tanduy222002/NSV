package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter

@AllArgsConstructor
public class AuthResponseDto implements Serializable {
    private Integer ID;
    private String token;
    private List<?> roles;
    private String message;
    @JsonProperty("refresh_token")
    private String refreshToken;



}
