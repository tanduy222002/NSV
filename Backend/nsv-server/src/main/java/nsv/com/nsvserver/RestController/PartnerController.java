package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Dto.CreatePartnerDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.Partner;
import nsv.com.nsvserver.Service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/partners")
@SecurityRequirement(name = "bearerAuth")
public class PartnerController {
    PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("")
    public ResponseEntity<?> addPartner(@RequestBody @Valid CreatePartnerDto createPartnerDto){
        partnerService.createPartner(createPartnerDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "partner successfully added");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPartner(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                        @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                        @RequestParam(required = false) String name, @RequestParam(required = false) String phone
    ){
        PageDto partners =partnerService.searchPartnerByFilterAndPagination(pageIndex,pageSize,name,phone);
        return ResponseEntity.ok(partners);
    }
}
