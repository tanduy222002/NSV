package nsv.com.nsvserver.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
        String message= warehouseService.createWarehouse(dto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", message);
        return ResponseEntity.ok(responseData);
    }


    @GetMapping("/variety")
    @Operation(summary = "Get variety of products")
    public ResponseEntity<?> getVariety() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/warehouse_status.json");
        List<String> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<String>>() {});
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}/statistics")
    @Operation(summary = "Get statistics of product in warehouse")
    public ResponseEntity<?> getStatistic(@PathVariable Integer id)  {
        List<StatisticOfProductInWarehouseDto> data=warehouseService.getStatisticOfProductInWarehouse(id);
        return ResponseEntity.ok(data);
    }
}
