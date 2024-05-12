package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import nsv.com.nsvserver.Dto.CreateExportTransferTicketDto;
import nsv.com.nsvserver.Dto.CreateTransferTicketDto;
import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Service.DebtService;
import nsv.com.nsvserver.Service.TransferTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/debts")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class DebtController {

    DebtService debtService;

    @Autowired
    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }


    @PutMapping("/{id}")
    @Operation(summary = "update debt")
    public ResponseEntity<?> putDebt(@PathVariable Integer id, @Valid @RequestBody DebtDetailDto updateDto){

        return ResponseEntity.ok(
                debtService.putDebt(id, updateDto.getName(), updateDto.getValue(), updateDto.getDescription(), updateDto.getCreate_date()
                ,updateDto.getDueDate(),updateDto.getIsPaid(), updateDto.getUnit())
        );
    }

}
