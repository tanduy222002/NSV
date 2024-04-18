package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreateMapDto;
import nsv.com.nsvserver.Dto.CreatePartnerDto;
import nsv.com.nsvserver.Service.MapService;
import nsv.com.nsvserver.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<?> addMap(@Valid @RequestBody CreateMapDto mapDto){
        String message = mapService.createMap(mapDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", message);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/{mapId}")
    public ResponseEntity<?> getMapDetail(@Valid @PathVariable Integer mapId){
        nsv.com.nsvserver.Entity.Map map = mapService.getMapById(mapId);
        return ResponseEntity.ok(map);
    }
}
