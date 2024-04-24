package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreateTransferTicketDto;
import nsv.com.nsvserver.Dto.CreateWarehouseDto;
import nsv.com.nsvserver.Service.TransferTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/transfer_ticket")
@SecurityRequirement(name = "bearerAuth")
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
}
