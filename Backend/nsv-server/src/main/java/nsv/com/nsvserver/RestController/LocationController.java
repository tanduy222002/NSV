package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.CreateDistrictDto;
import nsv.com.nsvserver.Dto.CreateProvinceDto;
import nsv.com.nsvserver.Dto.MessageDto;
import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Province;
import nsv.com.nsvserver.Entity.Ward;
import nsv.com.nsvserver.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@SecurityRequirement(name = "bearerAuth")
public class LocationController {
    AddressService addressService;

    @Autowired
    public LocationController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/provinces")
    public ResponseEntity<?> addProvince(@RequestBody @Valid CreateProvinceDto dto){
        addressService.addProvince(dto.getName());
        return ResponseEntity.ok(new MessageDto("province added successfully"));
    }

    @GetMapping("/provinces")
    public ResponseEntity<?> addProvince(){
        List<Province> provinces=addressService.getProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<?> addProvince(@PathVariable Integer id){
        Province province = addressService.getProvincesById(id);
        return ResponseEntity.ok(province);
    }
    @GetMapping("/provinces/{id}/districts")
    public ResponseEntity<?> getDistrictsInProvince(@PathVariable Integer id){
        List<District> districts = addressService.getDistrictInProvinceById(id);
        return ResponseEntity.ok(districts);
    }


    @PostMapping("/provinces/{id}/districts")
    public ResponseEntity<?> addDistrict(@PathVariable Integer id, @RequestBody @Valid CreateDistrictDto dto){
        addressService.addDistrict(dto.getName(),id);
        return ResponseEntity.ok(new MessageDto("district added successfully"));
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<?> addDistrict(@PathVariable Integer provinceId, @PathVariable Integer districtId){
        District district=addressService.findDistrictById(districtId);
        return ResponseEntity.ok(district);
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}/wards")
    public ResponseEntity<?> getWardsInDistrict(@PathVariable Integer provinceId, @PathVariable Integer districtId){
        List<Ward> wards=addressService.findWardInDistrictById(districtId);
        return ResponseEntity.ok(wards);
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}/wards/{wardId}")
    public ResponseEntity<?> getWardIdAndNameById(@PathVariable Integer provinceId, @PathVariable Integer districtId, @PathVariable Integer wardId){
        Ward ward=addressService.findWardIdAndNameById(wardId);
        return ResponseEntity.ok(ward);
    }


}
