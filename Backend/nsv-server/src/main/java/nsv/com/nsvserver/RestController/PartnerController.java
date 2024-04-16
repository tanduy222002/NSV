package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreatePartnerDto;
import nsv.com.nsvserver.Service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/partners")
@SecurityRequirement(name = "bearerAuth")
public class PartnerController {
    PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addPartner(@RequestBody @Valid CreatePartnerDto createPartnerDto){
        partnerService.createPartner(createPartnerDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "partner successfully added");
        return ResponseEntity.ok(responseData);
    }
}
