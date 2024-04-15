package nsv.com.nsvserver.RestController;

import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreatePartnerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PartnerController {
    @PostMapping
    public ResponseEntity<?> addPartner(@RequestBody @Valid CreatePartnerDto createPartnerDto){
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "partner successfully added");
        return ResponseEntity.ok(responseData);
    }
}
