package nsv.com.nsvserver;

import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.ClientImpl.AsyncEmail;
import nsv.com.nsvserver.Dto.GenerateOtpRequestDto;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.Otp;
import nsv.com.nsvserver.Entity.Profile;
import nsv.com.nsvserver.Exception.EmailNotFoundException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.OtpExpiredException;
import nsv.com.nsvserver.Exception.OtpNotMatchIdentifierException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.OtpRepository;
import nsv.com.nsvserver.Repository.RefreshTokenRepository;
import nsv.com.nsvserver.Service.ForgotPasswordService;
import nsv.com.nsvserver.Service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForgotPasswordServiceTest {

    private long OTP_EXPIRATION=8640000L;
    @Mock
    private EmailService emailService;
    @Mock
    private OtpRepository otpRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    private AsyncEmail asyncEmail;

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;

    @Test
    void generateOtpAndSendByEmail() throws Exception {
        GenerateOtpRequestDto otpRequest = new GenerateOtpRequestDto();
        otpRequest.setIdentifier("test@example.com");

        Employee employee = new Employee();


        when(employeeRepository.findByEmail(otpRequest.getIdentifier())).thenReturn(Optional.of(employee));  // Call the method under test
        Otp result = forgotPasswordService.generateOtpAndSendByEmail(otpRequest);

        // Verify the results
        assertNotNull(result);
        assertEquals(employee.getOtp().getCode(), result.getCode());

        verify(asyncEmail, times(1)).sendAsyncEmail(argThat(emailDetailDto->{
            return emailDetailDto.getRecipient().equals("test@example.com") ;
        }));

    }

    @Test
    void generateOtpAndSendByEmail_EmployeeNotFound_NotFoundExceptionThrown() throws Exception {
        GenerateOtpRequestDto otpRequest = new GenerateOtpRequestDto();
        otpRequest.setIdentifier("test@example.com");

        Employee employee = new Employee();


        when(employeeRepository.findByEmail(otpRequest.getIdentifier())).thenReturn(Optional.empty());  // Call the method under test

        Exception exception = assertThrows(EmailNotFoundException.class,()->{
            forgotPasswordService.generateOtpAndSendByEmail(otpRequest);
        });
    }

    @Test
    public void resetPassword() {
        // Mock data
        String newPassword = "newPassword";
        String otpCode = "123456";
        String identifier = "test@example.com";
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setEmail(identifier);
        employee.setProfile(profile);

        long expiryDateMillis = System.currentTimeMillis() + 10000; // Expiry date in the future
        Otp userOtp = new Otp();
        userOtp.setEmployee(employee);
        userOtp.setExpiryDate(new Date(expiryDateMillis));

        // Stubbing repository methods
        when(otpRepository.findByCode(otpCode)).thenReturn(Optional.of(userOtp));
        when(encoder.encode(newPassword)).thenReturn("encodedPassword");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the method under test
        String result = forgotPasswordService.resetPassword(newPassword, otpCode, identifier);

        // Verify repository method invocations
        verify(otpRepository).findByCode(otpCode);
        verify(employeeRepository).save(any(Employee.class));
//        verify(refreshTokenRepository).delete(any(RefreshToken.class));
        verify(otpRepository).delete(userOtp);

        // Assert the result
        assertEquals("New password was updated", result);
    }

    @Test
    public void resetPassword_OtpNotFound_NotFoundExceptionThrown() {
        // Mock data
        String newPassword = "newPassword";
        String otpCode = "123456";
        String identifier = "test@example.com";
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setEmail(identifier);
        employee.setProfile(profile);

        long expiryDateMillis = System.currentTimeMillis() + 10000; // Expiry date in the future
        Otp userOtp = new Otp();
        userOtp.setEmployee(employee);
        userOtp.setExpiryDate(new Date(expiryDateMillis));

        when(otpRepository.findByCode(otpCode)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,()->{
            forgotPasswordService.resetPassword(newPassword,otpCode,identifier);
        });
    }

    @Test
    public void resetPassword_OtpExpired_OtpExpiredException() {
        // Mock data
        String newPassword = "newPassword";
        String otpCode = "123456";
        String identifier = "test@example.com";
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setEmail(identifier);
        employee.setProfile(profile);

        long expiryDateMillis = System.currentTimeMillis() - 10000;
        Otp userOtp = new Otp();
        userOtp.setEmployee(employee);
        userOtp.setExpiryDate(new Date(expiryDateMillis));

        when(otpRepository.findByCode(otpCode)).thenReturn(Optional.of(userOtp));

        // Call the method under test
        Exception exception = assertThrows(OtpExpiredException.class,()->{
            forgotPasswordService.resetPassword(newPassword,otpCode,identifier);
        });

    }

    @Test
    public void resetPassword_wrongEmail_OtpMismatchExceptionThrown() {
        // Mock data
        String newPassword = "newPassword";
        String otpCode = "123456";
        String identifier = "test@example.com";
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setEmail("wrong@gmail.com");
        employee.setProfile(profile);

        long expiryDateMillis = System.currentTimeMillis() +10000;
        Otp userOtp = new Otp();
        userOtp.setEmployee(employee);
        userOtp.setExpiryDate(new Date(expiryDateMillis));

        when(otpRepository.findByCode(otpCode)).thenReturn(Optional.of(userOtp));

        // Call the method under test
        Exception exception = assertThrows(OtpNotMatchIdentifierException.class,()->{
            forgotPasswordService.resetPassword(newPassword,otpCode,identifier);
        });

    }

}