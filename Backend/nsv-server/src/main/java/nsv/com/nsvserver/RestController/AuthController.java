package nsv.com.nsvserver.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Otp;
import nsv.com.nsvserver.Service.AuthService;
import nsv.com.nsvserver.Service.ForgotPasswordService;
import nsv.com.nsvserver.Service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/api/auth")
public class AuthController {

    private AuthService authService;
    private RefreshTokenService refreshTokenService;

    private ForgotPasswordService forgotPasswordService;
    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, ForgotPasswordService forgotPasswordService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/login")
    @Operation(summary = "Get json web token by authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information"),
//            @ApiResponse(responseCode = "401", description = "Authorities is not authorized",
//                    content = @Content),
//            @ApiResponse(responseCode = "403", description = "User is not allowed to access this resource",
//                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not found"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        System.out.println("authenticateUser controller");
        AuthResponseDto authResponse = authService.authenticateUser(authRequest.getUserName(), authRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }


    @Operation(summary = "Sign up a new employee account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign up successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error",
                    content = @Content)

    })

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        AuthResponseDto authResponse = authService.signUp(signUpRequest.getUserName(), signUpRequest.getPassword(), signUpRequest.getRoles());
        AuthResponseDto authResponse = authService.signUp(signUpRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String newToken= refreshTokenService.refreshToken(refreshToken);
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(newToken,refreshToken);
        return ResponseEntity.ok(refreshTokenResponse);
    }


    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody GenerateOtpRequestDto otpRequest) {
        Otp otp=forgotPasswordService.generateOtpAndSendByEmail(otpRequest);
        GenerateOtpResponseDto res = new GenerateOtpResponseDto(otp.getExpiryDate(),"Otp is sent to email address: "+otpRequest.getIdentifier());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerifyRequestDto otpVerifyRequestDto) {
        String accessToken = forgotPasswordService.verifyOtp(otpVerifyRequestDto.getCode(),otpVerifyRequestDto.getIdentifier());
        OtpVerifyResponseDto res = new OtpVerifyResponseDto(accessToken, "Otp verify successfully");
        return ResponseEntity.ok(res);


    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordDto) {
        String message = forgotPasswordService.resetPassword(resetPasswordDto.getNewPassword(),resetPasswordDto.getConfirmNewPassword());
        ResetPasswordResponseDto res = new ResetPasswordResponseDto(message);
        return ResponseEntity.ok(res);

    }







}
