package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.ClientImpl.AsyncEmail;
import nsv.com.nsvserver.Dto.EmailDetailDto;
import nsv.com.nsvserver.Dto.GenerateOtpRequestDto;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.Otp;
import nsv.com.nsvserver.Exception.EmailNotFoundException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.OtpExpiredException;
import nsv.com.nsvserver.Exception.OtpNotMatchIdentifierException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.OtpRepository;
import nsv.com.nsvserver.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ForgotPasswordService {

    @Value("${OTP_EXPIRATION}")
    private long OTP_EXPIRATION;
    private  EmailService emailService;

    private OtpRepository otpRepository;

    private EmployeeRepository employeeRepository;

    private JwtTokenService jwtTokenService;

    private RefreshTokenRepository refreshTokenRepository;

    PasswordEncoder encoder;

    private AsyncEmail asyncEmail;

    @Autowired
    public ForgotPasswordService(EmailService emailService, OtpRepository otpRepository, EmployeeRepository employeeRepository, JwtTokenService jwtTokenService, RefreshTokenRepository refreshTokenRepository, PasswordEncoder encoder, AsyncEmail asyncEmail) {
        this.emailService = emailService;
        this.otpRepository = otpRepository;
        this.employeeRepository = employeeRepository;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.encoder = encoder;
        this.asyncEmail = asyncEmail;
    }

    @Transactional
    public Otp generateOtpAndSendByEmail(GenerateOtpRequestDto otpRequest) throws Exception{
        Otp otp = generateOtp(otpRequest.getIdentifier());
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setRecipient(otpRequest.getIdentifier());
        emailDetailDto.setSubject("Otp for reset password");
        String htmlContent = """
            <html lang="en">
            <head>
            <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>OTP Password</title>
            </head>
            <body>
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #333;">Your One-Time Password (OTP)</h2>
                    <p style="color: #555;">Dear {User},</p>
                    <p style="color: #555;">Your OTP password is: <strong>{OTP}</strong></p>
                    <p style="color: #555;">Please use this OTP to proceed with your action.</p>
                    <p style="color: #555;">If you didn't request this OTP, please ignore this email.</p>
                                <p style="color: #555;">Thank you!</p>
                </div>
            </body>
            </html>
        """;
        htmlContent=htmlContent.replace("{User}", emailDetailDto.getRecipient());
        htmlContent=htmlContent.replace("{OTP}",otp.getCode());

        emailDetailDto.setMsgBody(htmlContent);
        asyncEmail.sendAsyncEmail(emailDetailDto);
        return otp;


    }




//    public static String readHtmlTemplateFromFile(String filePath) throws IOException {
//        Path path = Paths.get(filePath);
//        byte[] bytes = Files.readAllBytes(path);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
    @Transactional
    public Otp generateOtp(String email){
        Employee employee = employeeRepository.findByEmail(email).
                orElseThrow(
                        ()->new EmailNotFoundException("No employee found with email: "+email)
                );

        String code = String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
        Otp otp =new Otp();
        otp.setCode(code);
        otp.setExpiryDate(
                new Date(System.currentTimeMillis()+ OTP_EXPIRATION)
        );

        if (employee.getOtp()!=null){
            otpRepository.delete(employee.getOtp());
        }
        employee.setOtp(otp);
        employeeRepository.save(employee);
        return otp;
    }

    @Transactional
    public String resetPassword(String newPassword, String otp, String identifier) {
        Otp userOtp = otpRepository.findByCode(otp).orElseThrow(
                ()->new NotFoundException("Otp does not exist")
        );
        if(userOtp.getExpiryDate().getTime()<System.currentTimeMillis()){
            throw new OtpExpiredException();
        }
        Employee employee =userOtp.getEmployee();
        String email = employee.getProfile().getEmail();
        if(!email.equals(identifier)){
            throw new OtpNotMatchIdentifierException();
        }
        employee.setPassword(encoder.encode(newPassword));
        refreshTokenRepository.delete(employee.getRefreshToken());
        otpRepository.delete(userOtp);
        employee.setRefreshToken(null);
        employee.setOtp(null);
        employeeRepository.save(employee);
        return "New password was updated";
    }
}
