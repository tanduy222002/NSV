package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Dto.CreateMapDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maps")
@SecurityRequirement(name = "bearerAuth")
public class MapController {

    MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("")
    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    public ResponseEntity<?> addMap(@Valid @RequestBody CreateMapDto mapDto){
        String message = mapService.createMap(mapDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", message);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/{mapId}")
    public ResponseEntity<?> getMapDetail(@Valid @PathVariable Integer mapId){
        CreateMapDto mapDto = mapService.getMapById(mapId);
        return ResponseEntity.ok(mapDto);
    }
    @Operation(summary = "Get list of map by name")
    @GetMapping("/search")
    public ResponseEntity<?> searchMapByFilterAndPagination(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                                            @RequestParam(required = false) String name){
        PageDto result = mapService.searchMapByFilterAndPagination(pageIndex, pageSize, name);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get list of unused map")
    @GetMapping("/unused")
    public ResponseEntity<?> searchUnusedMapByFilterAndPagination(){
        List<?> result = mapService.searchMapByFilterAndPagination();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get slot in map")
    @GetMapping("/{mapId}/slots")
    public ResponseEntity<?> getSlotsInMapByFilterAndPagination(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                                            @RequestParam(required = false) String name,
                                                            @PathVariable Integer mapId){
        PageDto result = mapService.getSlotInMapByFilterAndPagination(pageIndex, pageSize, name,mapId);
        return ResponseEntity.ok(result);
    }
}
