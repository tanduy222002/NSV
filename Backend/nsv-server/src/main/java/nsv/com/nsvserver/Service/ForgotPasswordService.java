package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.Dto.EmailDetail;
import nsv.com.nsvserver.Dto.GenerateOtpRequestDto;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.EmployeeDetail;
import nsv.com.nsvserver.Entity.Otp;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.OtpExpiredException;
import nsv.com.nsvserver.Exception.OtpNotMatchIdentifierException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ForgotPasswordService {

    @Value("${OTP_EXPIRATION}")
    private long OTP_EXPIRATION;
    private  EmailService emailService;

    private OtpRepository otpRepository;

    private EmployeeRepository employeeRepository;

    private JwtTokenService jwtTokenService;

    PasswordEncoder encoder;

    @Autowired
    public ForgotPasswordService(PasswordEncoder encoder,EmailService emailService, EmployeeRepository employeeRepository, OtpRepository otpRepository, JwtTokenService jwtTokenService) {
        this.emailService = emailService;
        this.employeeRepository = employeeRepository;
        this.otpRepository = otpRepository;
        this.jwtTokenService=jwtTokenService;
        this.encoder=encoder;
    }

    @Transactional
    public Otp generateOtpAndSendByEmail(GenerateOtpRequestDto otpRequest){
        try{
            Otp otp = generateOtp(otpRequest.getIdentifier());
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(otpRequest.getIdentifier());
        emailDetail.setSubject("Otp for reset password");
        // Read the HTML template into a String variable
        Path currentPath = Paths.get( "src", "main", "resources","templates", "generate-otp-template.html");
        String absolutePath = currentPath.toAbsolutePath().toString();

        String htmlContent = readHtmlTemplateFromFile(absolutePath);
        htmlContent=htmlContent.replace("{User}", emailDetail.getRecipient());
        htmlContent=htmlContent.replace("{OTP}",otp.getCode());

        emailDetail.setMsgBody(htmlContent);
        emailService.sendEmailWithHtmlContent(emailDetail);
            return otp;
        }

        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String verifyOtp(String otpCode, String identifier){
        Otp userOtp = otpRepository.findByCode(otpCode).orElseThrow(
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

        String token = jwtTokenService.generateToken(employee);
        return token;
    }

    public static String readHtmlTemplateFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public Otp generateOtp(String email){
        String code = String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
        Otp otp =new Otp();
        otp.setCode(code);
        otp.setExpiryDate(
                new Date(System.currentTimeMillis()+ OTP_EXPIRATION)
        );
        Employee employee = employeeRepository.findByEmail(email).
                orElseThrow(()->new NotFoundException("No employee found with email: " + email));
        if (employee.getOtp()!=null){
            otpRepository.delete(employee.getOtp());
        }
        employee.setOtp(otp);
        employeeRepository.save(employee);
        return otp;
    }

    @Transactional
    public String resetPassword(String newPassword, String confirmNewPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetail employeeDetail = (EmployeeDetail) authentication.getPrincipal();
        Employee employee=employeeDetail.getEmployee();
                employee.setPassword(encoder.encode(newPassword));
        employeeRepository.save(employee);
        return "New password was updated";
    }
}
