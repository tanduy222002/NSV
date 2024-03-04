package nsv.com.nsvserver.Dto;

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
    private String refreshToken;



}
