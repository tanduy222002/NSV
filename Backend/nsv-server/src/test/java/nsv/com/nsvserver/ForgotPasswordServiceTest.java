package nsv.com.nsvserver;

import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.ClientImpl.AsyncEmail;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.EmployeeService;
import nsv.com.nsvserver.Service.ForgotPasswordService;
import nsv.com.nsvserver.Service.JwtTokenService;
import nsv.com.nsvserver.Util.EmployeeRoles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
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

}