package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.ProvinceResponseDto;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Province;
import nsv.com.nsvserver.Entity.Ward;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.AddressRepository;
import nsv.com.nsvserver.Repository.DistrictRepository;
import nsv.com.nsvserver.Repository.ProvinceRepository;
import nsv.com.nsvserver.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    ProvinceRepository provinceRepository;
    WardRepository wardRepository;

    DistrictRepository districtRepository;

    AddressRepository addressRepository;

    @Autowired
    public AddressService(ProvinceRepository provinceRepository, WardRepository wardRepository, DistrictRepository districtRepository, AddressRepository addressRepository) {
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.addressRepository = addressRepository;
    }

    public void addProvince(String provinceName){
        if(provinceRepository.existsByName(provinceName)){
            throw new ExistsException("Province already exists: " + provinceName);
        };
        Province province = new Province();
        province.setName(provinceName);
        provinceRepository.save(province);
    }

    public List<Province> getProvinces(){
        List<Province> provinces = provinceRepository.findAllIdAndName();
        return provinces;
    }

    public void addDistrict(String districtName, Integer provinceId){
        Optional<Province> province =provinceRepository.findById(provinceId);
        if(province.get() == null){
            throw new NotFoundException("Province not found: "+provinceId);
        };

        District district =new District();
        district.setName(districtName);
        district.setProvince(province.get());
        districtRepository.save(district);

    }

    public Province getProvincesById(Integer id) {
        Province province =provinceRepository.findProvinceNameAndIdById(id);
        if(province==null)
            throw new NotFoundException("Province not found");
        System.out.println("after fetch");
        return province;
    }

    public District findDistrictById(Integer id) {
        District district = districtRepository.findDistrictNameAndId(id);
        if(district==null)
            throw new NotFoundException("Province not found");
        System.out.println("after fetch");
        return district;

    }

    public List<District> getDistrictInProvinceById(Integer id) {
        return districtRepository.findDistrictsInProvince(id);

    }

    public List<Ward> findWardInDistrictById(Integer districtId) {
        return wardRepository.findWardsInDistrict(districtId);
    }

    public Ward findWardIdAndNameById(Integer wardId) {
        return wardRepository.findWardIdAndNameById(wardId);
    }

    @Transactional
    public Address createAddress(String streetAddress, Integer wardId, Integer districtId, Integer provinceId) {
        Address address = new Address(streetAddress);
        Ward ward = wardRepository.findById(provinceId).orElseThrow(()->new NotFoundException("Province not found: "+provinceId));
        address.setWard(ward);
        addressRepository.save(address);
        return address;
    }
}
