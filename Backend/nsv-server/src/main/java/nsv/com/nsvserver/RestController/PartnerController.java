package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Dto.CreatePartnerDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    @Operation(description = "Create a new partner")
    public ResponseEntity<?> addPartner(@RequestBody @Valid CreatePartnerDto createPartnerDto){
        partnerService.createPartner(createPartnerDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "partner successfully added");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search")
    @Operation(description = "get partners with filter(name,phone) and pagination")
    public ResponseEntity<?> getPartner(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                        @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                        @RequestParam(required = false) String name, @RequestParam(required = false) String phone
    ){
        PageDto partners =partnerService.searchPartnerByFilterAndPagination(pageIndex,pageSize,name,phone);
        return ResponseEntity.ok(partners);
    }

    @GetMapping("/statistic/search")
    @Operation(description = "get partners with statistics information ")
    public ResponseEntity<?> getPartnersStatistic(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                        @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                        @RequestParam(required = false) String name, @RequestParam(required = false) String phone
    ){
        PageDto partners =partnerService.getPartnersStatisticByFilterAndPagination(pageIndex,pageSize,name,phone);
        return ResponseEntity.ok(partners);
    }


    @GetMapping("/{id}")
    @Operation(description = "get partners with statistics information ")
    public ResponseEntity<?> getPartnersStatistic(@PathVariable Integer id
    ){
        return ResponseEntity.ok(partnerService.getPartnerDetailById(id));
    }

    @GetMapping("/{id}/transactions")
    @Operation(description = "get partners with statistics information ")
    public ResponseEntity<?> get(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isPaid
    )
    {
        return ResponseEntity.ok(partnerService.getPartnerTransactionById(pageIndex, pageSize,id, name, isPaid));
    }

    @GetMapping("/{id}/debts")
    @Operation(description = "get partners with statistics information ")
    public ResponseEntity<?> get(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false) Boolean isPaid
    )
    {
        return ResponseEntity.ok(partnerService.getPartnerDebtById(pageIndex, pageSize,id, isPaid));
    }

//    @GetMapping("/test")
//    @Secured({ "ROLE_MANAGER", "ROLE_EMPLOYEE" })
//    @Operation(description = "get partners with statistics information ")
//    public ResponseEntity<?> getTest()
//    {
//        return ResponseEntity.ok("ok");
//    }
}
