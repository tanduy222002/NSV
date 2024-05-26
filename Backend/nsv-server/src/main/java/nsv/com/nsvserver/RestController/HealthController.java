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
@RequestMapping(value = "/health")
public class HealthController {
    @GetMapping("/health-check")
    @Operation(summary = "check this server is active")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Server active");

    }







}
