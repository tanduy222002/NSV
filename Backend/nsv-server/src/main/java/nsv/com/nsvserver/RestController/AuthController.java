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
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/auth")
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

    @PostMapping("/sign-in")
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

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {
        System.out.println("authenticateUser controller");
        AuthResponseDto authResponse = authService.authenticateUser(authRequestDto.getUsername(), authRequestDto.getPassword());
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

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
//        AuthResponseDto authResponse = authService.signUp(signUpRequestDto.getUserName(), signUpRequestDto.getPassword(), signUpRequestDto.getRoles());
        AuthResponseDto authResponse = authService.signUp(signUpRequestDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Get json web token by sending refresh token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String newToken= refreshTokenService.refreshToken(refreshToken);
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(newToken,refreshToken);
        return ResponseEntity.ok(refreshTokenResponse);
    }


    @PostMapping("/request-otp")
    @Operation(summary = "Send request otp to get otp code by sending email address")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody GenerateOtpRequestDto otpRequest) throws Exception {
        Otp otp=forgotPasswordService.generateOtpAndSendByEmail(otpRequest);
        GenerateOtpResponseDto res = new GenerateOtpResponseDto(otp.getExpiryDate(),"Otp is sent to email address: "+otpRequest.getIdentifier());
        return ResponseEntity.ok(res);
    }




    @PostMapping("/reset-password")
    @Operation(summary = "Renew password with token got from verify otp")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordDto) {
        String message = forgotPasswordService.resetPassword(
                resetPasswordDto.getNewPassword(),
                resetPasswordDto.getCode(),
                resetPasswordDto.getIdentifier()
        );
        ResetPasswordResponseDto res = new ResetPasswordResponseDto(message);
        return ResponseEntity.ok(res);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/log-out")
    @Operation(summary = "log out account")
    public ResponseEntity<?> logout() {
        authService.logOut();
        return ResponseEntity.ok("Logout successfully");

    }







}
