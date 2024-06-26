package nsv.com.nsvserver.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Annotation.TraceTime;
import nsv.com.nsvserver.Dto.CreateMapResponseDto;
import nsv.com.nsvserver.Dto.CreateWarehouseDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Service.SlotService;
import nsv.com.nsvserver.Service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/warehouses")
@SecurityRequirement(name = "bearerAuth")
public class WarehouseController {

    WarehouseService warehouseService;
    SlotService slotService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService, SlotService slotService) {
        this.warehouseService = warehouseService;
        this.slotService = slotService;
    }

    @PostMapping("")
    @Operation(summary = "Create new warehouse")
    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody CreateWarehouseDto dto){
        CreateMapResponseDto responseDto = warehouseService.createWarehouse(dto);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/status")
    @Operation(summary = "Get status type of warehouse ")
    public ResponseEntity<?> getStatus() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/warehouse_status.json");
        List<String> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<String>>() {});
        return ResponseEntity.ok(data);
    }


    @GetMapping("/types")
    @Operation(summary = "Get types of warehouse ")
    public ResponseEntity<?> getTypes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/warehouse_type.json");
        List<String> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<String>>() {});
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}/products/statistics")
    @Operation(summary = "Get statistics of product in warehouse")
    public ResponseEntity<?> getStatistic(@PathVariable Integer id)  {
        List<StatisticOfProductInWarehouseDto> data=warehouseService.getStatisticOfProductInWarehouse(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detail in warehouse")
    public ResponseEntity<?> getSlotsInWarehouse(@PathVariable Integer id)  {
        return ResponseEntity.ok(warehouseService.getWarehouseDetail(id));
    }

    @TraceTime
    @GetMapping("/{warehouseId}/slots/{slotId}/statistics")
    @Operation(summary = "Get statistics in warehouse slot has id = :slotId ")
    public ResponseEntity<?> getSlotsInWarehouse(@PathVariable Integer warehouseId, @PathVariable Integer slotId) throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(slotService.getQualityStatisticInSlot(slotId));
    }

    @GetMapping("/name_and_id")
    @Operation(summary = "Get list of warehouse name and id")
    public ResponseEntity<?> getWarehouseNameAndId()  {
        return ResponseEntity.ok(warehouseService.getWarehouseNameAndId());
    }

    @GetMapping("/suitable-for-product-type/{typeId}")
    @Operation(summary = "Get list of warehouse that suitable to store product")
    public ResponseEntity<?> getSuitableForProductType(@PathVariable Integer typeId)  {
        return ResponseEntity.ok(warehouseService.getWarehouseSuitableForProduct(typeId));
    }


    @GetMapping("/search")
    @Operation(summary = "Get list of warehouse with filter and pagination")
    public ResponseEntity<PageDto> getWarehouses(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status)  {
        return ResponseEntity.ok(
                warehouseService.getWarehouses(pageIndex, pageSize, name, type, status)
        );
    }

    @GetMapping("/{warehouseId}/slots")
    @Operation(summary = "Get list of slot in warehouse has: warehouseId")
    public ResponseEntity<?> getWarehouseSlots(@PathVariable Integer warehouseId)  {

        return ResponseEntity.ok(warehouseService.getWarehouseSlots(warehouseId));
    }

    @GetMapping("/{warehouseId}/slots/{slotId}/bins")
    @Operation(summary = "Get bins in slot located in warehouse")
    public ResponseEntity<?> getBinInSlotInWarehouse(@PathVariable Integer warehouseId, @PathVariable Integer slotId,
                                                     @RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                                     @RequestParam(defaultValue = "5") @Min(1) Integer pageSize)  {
        return ResponseEntity.ok(slotService.getSlotDetail(slotId, pageSize, pageIndex));
    }

}
