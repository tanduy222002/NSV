package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bins")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class BinController {

    BinService binService;

    @Autowired
    public BinController(BinService binService) {
        this.binService = binService;
    }

    @GetMapping("/search")
    @Operation(summary = "get bin with filter and pagination")
    public ResponseEntity<?> getTransferTicketWithFilterAndPagination(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false) @Schema(description = "warehouse that contains bin") Integer warehouseId,
            @RequestParam(required = false) @Schema(description = "product of bin") Integer productId,
            @RequestParam(required = false) @Schema(description = "type of product of bin") Integer typeId,
            @RequestParam(required = false) @Schema(description = "quality of type") Integer qualityId,
            @RequestParam(required = false) @Schema(description = "min weight of bin") Integer minWeight,
            @RequestParam(required = false) @Schema(description = "min weight of bin") Integer maxWeight)
    {
        return ResponseEntity.ok(
                binService.findBinWithFilterAndPagination(
                        pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight)
                );

    }
}
