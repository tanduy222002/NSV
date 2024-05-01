package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import nsv.com.nsvserver.Dto.CreateTransferTicketDto;
import nsv.com.nsvserver.Service.TransferTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/transfer_ticket")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class TransferTicketController {

    TransferTicketService transferTicketService;

    @Autowired
    public TransferTicketController(TransferTicketService transferTicketService) {
        this.transferTicketService = transferTicketService;
    }

    @PostMapping("/import_tickets")
    @Operation(summary = "Create import ticket")
    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    public ResponseEntity<?> createImportTicket(@Valid @RequestBody CreateTransferTicketDto dto){
        String message= transferTicketService.createImportTransferTicket(dto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", message);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}/status/approved")
    @Operation(summary = "Approve import ticket")
    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    public ResponseEntity<?> approveTicketStatus(@PathVariable Integer id){
        transferTicketService.approveTicketStatus(id);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "approved for ticket successfully");
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/search")
    @Operation(summary = "get transfer ticket with filter and pagination")
    public ResponseEntity<?> getTransferTicketWithFilterAndPagination(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false) @Schema(description = "name of ticket") String name,
            @RequestParam(required = false) @Pattern(regexp = "^(IMPORT|EXPORT)$") @Schema(description = "type of ticket: IMPORT/EXPORT") String type,
            @RequestParam(required = false) @Pattern(regexp = "^(PENDING|APPROVED|REJECTED)$") @Schema(description = "status of ticket: APPROVED/PENDING/REJECTED") String status)
    {

        return ResponseEntity.ok(
                transferTicketService.getTransferTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status)
        );
    }
}
