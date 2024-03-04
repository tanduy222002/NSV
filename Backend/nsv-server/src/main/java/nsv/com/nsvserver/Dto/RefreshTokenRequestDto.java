package nsv.com.nsvserver.Dto;

import lombok.Data;

@Data
public class RefreshTokenRequestDto {
    private String refreshToken;

    public RefreshTokenRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenRequestDto() {
    }
}
