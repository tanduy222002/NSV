package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.AuthResponseDto;
import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Dto.SignUpRequestDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.AccountSuspendedException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.UserNameExistsException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.AuthService;
import nsv.com.nsvserver.Service.DebtService;
import nsv.com.nsvserver.Service.JwtTokenService;
import nsv.com.nsvserver.Service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtTokenService jwtTokenService;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    RefreshTokenService refreshTokenService;
    @Mock
    ProfileRepository profileRepository;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }
    @InjectMocks
    AuthService authService;

    @Test
    public void authenticateUser_whenUserIsSuspended_AccountSuspendedExceptionThrown() {
        // Arrange
        String userName = "user";
        String password = "password";
        Employee employee = new Employee();
        employee.setStatus("SUSPENDED");

        EmployeeDetail employeeDetail = new EmployeeDetail(employee);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(employeeDetail);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act & Assert
        AccountSuspendedException exception = assertThrows(AccountSuspendedException.class, () -> {
            authService.authenticateUser(userName, password);
        });

        assertNotNull(exception);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(refreshTokenService, never()).generateRefreshToken(any(Employee.class));
    }

    @Test
    public void authenticateUser_UserIsNotSuspended_returnsAuthResponseDto() {
        // Arrange
        String userName = "user";
        String password = "password";
        String jwt = "jwt-token";
        String refreshTokenString = "refresh-token";

        Employee employee = new Employee();
        employee.setId(1);
        employee.setStatus("ACTIVE");
        employee.setRoles(Arrays.asList(new Role("ROLE_USER")));

        EmployeeDetail employeeDetail = new EmployeeDetail(employee);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(employeeDetail);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenService.generateToken(employee)).thenReturn(jwt);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        //when(refreshTokenService.generateRefreshToken(employee)).thenReturn(refreshToken);
        when(refreshTokenService.generateRefreshToken(employee)).thenAnswer(invocation -> {
            // Additional actions
            employee.setRefreshToken(refreshToken);
            // Return the mock refresh token
            return refreshToken;
        });

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuthResponseDto response = authService.authenticateUser(userName, password);

        // Assert
        assertNotNull(response);
        assertEquals(employee.getId(), response.getID());
        assertEquals(jwt, response.getToken());
        assertEquals(Arrays.asList("ROLE_USER"), response.getRoles());
        assertEquals("Login successfully", response.getMessage());


        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenService, times(1)).generateToken(employee);
        verify(refreshTokenService, times(1)).generateRefreshToken(employee);
    }

    @Test
    public void authenticateUser_RefreshTokenExists_returnsAuthResponseDto() {
        // Arrange
        String userName = "user";
        String password = "password";
        String jwt = "jwt-token";
        String refreshTokenString = "existing-refresh-token";

        Employee employee = new Employee();
        employee.setId(1);
        employee.setStatus("ACTIVE");
        employee.setRoles(Arrays.asList(new Role("ROLE_USER")));
        RefreshToken existingRefreshToken = new RefreshToken();
        existingRefreshToken.setToken(refreshTokenString);
        employee.setRefreshToken(existingRefreshToken);

        EmployeeDetail employeeDetail = new EmployeeDetail(employee);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(employeeDetail);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenService.generateToken(employee)).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuthResponseDto response = authService.authenticateUser(userName, password);

        // Assert
        assertNotNull(response);
        assertEquals(employee.getId(), response.getID());
        assertEquals(jwt, response.getToken());
        assertEquals(Arrays.asList("ROLE_USER"), response.getRoles());
        assertEquals("Login successfully", response.getMessage());
        assertEquals(refreshTokenString, response.getRefreshToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenService, times(1)).generateToken(employee);
        verify(refreshTokenService, never()).generateRefreshToken(employee);
    }

    @Test
    public void logOut_DeleteRefreshToken() {
        // Arrange
        Employee employee = new Employee();
        EmployeeDetail employeeDetail = new EmployeeDetail(employee);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(employeeDetail);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(employeeDetail);

        // Act
        authService.logOut();

        // Assert
        verify(refreshTokenRepository, times(1)).deleteByEmployee(employee);
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getPrincipal();
    }

    @Test
    public void signUp_whenUsernameExists_UserNameExistsExceptionThrown() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("testuser","password","password", "test@example.com", Arrays.asList("ROLE_USER"));

        // Arrange
        when(employeeRepository.existsByUserName(signUpRequestDto.getUserName())).thenReturn(true);

        // Act & Assert
        assertThrows(UserNameExistsException.class, () -> {
            authService.signUp(signUpRequestDto);
        });

        verify(employeeRepository, times(1)).existsByUserName(signUpRequestDto.getUserName());
    }

    @Test
    public void signUp_whenEmailExists_UserNameExistsExceptionThrown() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("testuser","password","password", "test@example.com", Arrays.asList("ROLE_USER"));
        // Arrange
        when(employeeRepository.existsByUserName(signUpRequestDto.getUserName())).thenReturn(false);
        when(profileRepository.existsByEmail(signUpRequestDto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(UserNameExistsException.class, () -> {
            authService.signUp(signUpRequestDto);
        });

        verify(employeeRepository, times(1)).existsByUserName(signUpRequestDto.getUserName());
        verify(profileRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
    }

    @Test
    public void signUp_whenValidRequest_shouldCreateEmployee() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("testuser","password","password", "test@example.com", Arrays.asList("ROLE_USER"));

        // Arrange
        String encodedPassword = "encodedPassword";
        String jwt = "jwt-token";
        String refreshTokenString = "refresh-token";

        when(employeeRepository.existsByUserName(signUpRequestDto.getUserName())).thenReturn(false);
        when(profileRepository.existsByEmail(signUpRequestDto.getEmail())).thenReturn(false);
        when(encoder.encode(signUpRequestDto.getPassword())).thenReturn(encodedPassword);
        when(roleRepository.findByName("ROLE_EMPLOYEE")).thenReturn(null);

        Role role = new Role("ROLE_EMPLOYEE");
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(jwtTokenService.generateToken(any(Employee.class))).thenReturn(jwt);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        when(refreshTokenService.generateRefreshToken(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            employee.setRefreshToken(refreshToken);
            return refreshToken;
        });

        // Act
        AuthResponseDto responseDto = authService.signUp(signUpRequestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Signup successfully", responseDto.getMessage());
        assertEquals(jwt, responseDto.getToken());
        assertEquals(refreshTokenString, responseDto.getRefreshToken());

        verify(employeeRepository, times(1)).existsByUserName(signUpRequestDto.getUserName());
        verify(profileRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
        verify(encoder, times(1)).encode(signUpRequestDto.getPassword());
        verify(roleRepository, times(1)).findByName("ROLE_EMPLOYEE");
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(refreshTokenService, times(1)).generateRefreshToken(any(Employee.class));
        verify(jwtTokenService, times(1)).generateToken(any(Employee.class));
    }


}
