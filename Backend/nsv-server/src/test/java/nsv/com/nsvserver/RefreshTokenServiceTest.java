package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Entity.Debt;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.RefreshToken;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.RefreshTokenExpiredException;
import nsv.com.nsvserver.Repository.DebtRepository;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.RefreshTokenRepository;
import nsv.com.nsvserver.Service.DebtService;
import nsv.com.nsvserver.Service.JwtTokenService;
import nsv.com.nsvserver.Service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    Long  REFRESH_TOKEN_EXPIRATION = 60480000L;


    @Test
    public void generateRefreshToken_generateAndSaveRefreshToken() {
        // Arrange
        Employee employee = new Employee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(employee);

        // Assert
        assertNotNull(refreshToken);
        assertNotNull(refreshToken.getToken());
        assertNotNull(refreshToken.getExpiryDate());
        assertEquals(employee,refreshToken.getEmployee());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void refreshToken_ReturnNewToken() {
        // Arrange
        String refreshTokenString = UUID.randomUUID().toString();
        Employee employee = new Employee();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        refreshToken.setEmployee(employee);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));

        when(refreshTokenRepository.findByToken(refreshTokenString)).thenReturn(Optional.of(refreshToken));
        when(jwtTokenService.generateToken(employee)).thenReturn("newJwtToken");

        // Act
        String token = refreshTokenService.refreshToken(refreshTokenString);

        // Assert
        assertNotNull(token);
        assertEquals("newJwtToken", token);
        verify(refreshTokenRepository, times(1)).findByToken(refreshTokenString);
        verify(jwtTokenService, times(1)).generateToken(employee);
    }

    @Test
    public void refreshToken_RefreshTokenNotFound_NotFoundExceptionThrown() {
        // Arrange
        String refreshTokenString = UUID.randomUUID().toString();
        when(refreshTokenRepository.findByToken(refreshTokenString)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> refreshTokenService.refreshToken(refreshTokenString));
        verify(refreshTokenRepository, times(1)).findByToken(refreshTokenString);
    }

    @Test
    public void refreshToken_RefreshTokenExpired_RefreshTokenExpiredExceptionThrown() {
        // Arrange
        String refreshTokenString = UUID.randomUUID().toString();
        Employee employee = new Employee();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        refreshToken.setEmployee(employee);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() - 1000)); // Already expired

        when(refreshTokenRepository.findByToken(refreshTokenString)).thenReturn(Optional.of(refreshToken));

        // Act & Assert
        assertThrows(RefreshTokenExpiredException.class, () -> refreshTokenService.refreshToken(refreshTokenString));
        verify(refreshTokenRepository, times(1)).findByToken(refreshTokenString);
        verify(refreshTokenRepository, times(1)).delete(refreshToken);
    }


}
