package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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



    @DeleteMapping("/{id}")
    @Operation(summary = "delete debt by id")
    public ResponseEntity<?> deleteDebt(@PathVariable Integer id){

        return ResponseEntity.ok(
               debtService.deleteDebt(id)
        );
    }

}
