package nsv.com.nsvserver.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreateMapResponseDto;
import nsv.com.nsvserver.Dto.CreateWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouses")
@SecurityRequirement(name = "bearerAuth")
public class WarehouseController {

    WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
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

    @GetMapping("/name_and_id")
    @Operation(summary = "Get list of warehouse name and id")
    public ResponseEntity<?> getWarehouseNameAndId()  {
        warehouseService.getWarehouseNameAndId();
        return ResponseEntity.ok(warehouseService.getWarehouseNameAndId());
    }

    @GetMapping("/{warehouseId}/slots/")
    @Operation(summary = "Get list of slot in warehouse has: warehouseId")
    public ResponseEntity<?> getWarehouseSlots(@PathVariable Integer warehouseId)  {

        return ResponseEntity.ok(warehouseService.getWarehouseSlots(warehouseId));
    }

}
