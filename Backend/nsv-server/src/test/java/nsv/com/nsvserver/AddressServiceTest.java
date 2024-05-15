package nsv.com.nsvserver;

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
import nsv.com.nsvserver.Service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    ProvinceRepository provinceRepository;
    @Mock
    WardRepository wardRepository;
    @Mock
    DistrictRepository districtRepository;
    @Mock
    AddressRepository addressRepository;
    @InjectMocks
    AddressService addressService = new AddressService(provinceRepository,wardRepository,districtRepository,addressRepository);

    @Test
    public void addProvince_ProvinceNameExisted_ExistExceptionThrown(){
        //Prepare data
        final String provinceName = "Ho Chi Minh";
        //Simulate method
        Mockito.when(provinceRepository.existsByName(provinceName)).thenReturn(true);

        // assert that exception is thrown
        Exception exception = assertThrows(ExistsException.class, () -> {
            addressService.addProvince(provinceName);
        });

        //verify method is called
        verify(provinceRepository).existsByName(provinceName);

        verify(provinceRepository, never()).save(any(Province.class));
        // assert that exception is thrown with correct message
        assertThat(exception.getMessage()).isEqualTo("Province already exists: " + provinceName);

    }

    @Test
    public void addProvince_ProvinceWasNotExisted_SaveProvince(){
        //Prepare data
        final String provinceName = "Ho Chi Minh";
        //Simulate method
        Mockito.when(provinceRepository.existsByName(provinceName)).thenReturn(false);

        //Call method
        addressService.addProvince(provinceName);

        //verify method is called
        verify(provinceRepository).existsByName(provinceName);

        // Assert
        verify(provinceRepository).save(argThat(province -> province.getName().equals(provinceName)));

    }

    @Test
    public void getProvince_NormalCase_GetResultList(){
        final String province1Name = "Ho Chi Minh";
        final String province2Name = "Ha Noi";

        Province province1 = new Province();
        province1.setId(1);
        province1.setName("Ho Chi Minh");

        Province province2 = new Province();
        province2.setId(2);
        province2.setName("Ha Noi");

        List<Province> expectedProvinces = Arrays.asList(province1, province2);
        Mockito.when(provinceRepository.findAllIdAndName()).thenReturn(expectedProvinces);

        List<Province> provinces=addressService.getProvinces();

        verify(provinceRepository).findAllIdAndName();

        assertThat(provinces.size()).isEqualTo(2);
        assertThat(provinces.get(0).getName()).isEqualTo(province1Name);
        assertThat(provinces.get(1).getName()).isEqualTo(province2Name);
    }

    @Test
    public void addDistrict_ProvinceNotFound_NotFoundExceptionThrown() {

        String districtName = "Thu Duc";
        Integer provinceId = 1;
        Mockito.when(provinceRepository.findById(provinceId)).thenReturn(Optional.empty());


        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            addressService.addDistrict(districtName, provinceId);
        });
        verify(provinceRepository, times(1)).findById(provinceId);
        verify(districtRepository, never()).save(any(District.class));

        assertEquals("Province not found: 1", exception.getMessage());

    }
    @Test
    public void addDistrict_ProvinceFound_savesDistrict() {
        // Arrange
        String districtName = "Thu Duc";
        Integer provinceId = 1;
        Province province = new Province();
        province.setId(provinceId);
        province.setName(districtName);

        when(provinceRepository.findById(provinceId)).thenReturn(Optional.of(province));

        // Act
        addressService.addDistrict(districtName, provinceId);

        // Assert
        verify(provinceRepository, times(1)).findById(provinceId);
        verify(districtRepository).save(argThat(district ->
                district.getName().equals(districtName) &&
                        district.getProvince().equals(province)
        ));
    }

    @Test
    public void getProvincesById_ProvinceNotFound_NotFoundExceptionThrown() {
        // Arrange
        Integer provinceId = 1;
        when(provinceRepository.findProvinceNameAndIdById(provinceId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            addressService.getProvincesById(provinceId);
        });
        verify(provinceRepository, times(1)).findProvinceNameAndIdById(provinceId);

        assertEquals("Province not found", exception.getMessage());

    }

    @Test
    public void getProvincesById_ProvinceFound_getSingleResult() {
        // Arrange
        Integer provinceId = 1;
        Province province = new Province();
        province.setId(provinceId);
        province.setName("Thu Duc");
        when(provinceRepository.findProvinceNameAndIdById(provinceId)).thenReturn(province);

        // Act
        Province foundProvince = addressService.getProvincesById(provinceId);

        verify(provinceRepository, times(1)).findProvinceNameAndIdById(provinceId);

        // Assert
        assertNotNull(foundProvince);
        assertEquals(provinceId, foundProvince.getId());
        assertEquals("Thu Duc", foundProvince.getName());

    }
    @Test
    public void findDistrictById_DistrictExists_GetSingleResult() {
        Integer districtId = 1;
        District mockDistrict = new District();
        mockDistrict.setId(districtId);
        mockDistrict.setName("Test District");

        when(districtRepository.findDistrictNameAndId(districtId)).thenReturn(mockDistrict);

        District result = addressService.findDistrictById(districtId);

        assertNotNull(result);
        assertEquals(districtId, result.getId());
        assertEquals("Test District", result.getName());
        verify(districtRepository, times(1)).findDistrictNameAndId(districtId);
    }

    @Test
    public void FindDistrictById_DistrictNotFound_NotFoundExceptionThrown() {
        Integer districtId = 2;

        when(districtRepository.findDistrictNameAndId(districtId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            addressService.findDistrictById(districtId);
        });

        assertEquals("Province not found", exception.getMessage());
        verify(districtRepository, times(1)).findDistrictNameAndId(districtId);
    }

    @Test
    public void GetDistrictInProvinceById_DistrictFound_GetSingleResult() {
        Integer provinceId = 1;
        District district1 = new District(1, "District 1");
        District district2 = new District(2, "District 2");
        List<District> districts = Arrays.asList(district1, district2);

        when(districtRepository.findDistrictsInProvince(provinceId)).thenReturn(districts);

        List<District> result = addressService.getDistrictInProvinceById(provinceId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("District 1", result.get(0).getName());
        assertEquals("District 2", result.get(1).getName());
        verify(districtRepository, times(1)).findDistrictsInProvince(provinceId);
    }

    @Test
    public void FindWardInDistrictById_WardFound_GetSingleResult() {
        Integer districtId = 1;
        Ward ward1 = new Ward(1, "Ward 1");
        Ward ward2 = new Ward(2, "Ward 2");
        List<Ward> wards = Arrays.asList(ward1, ward2);

        when(wardRepository.findWardsInDistrict(districtId)).thenReturn(wards);

        List<Ward> result = addressService.findWardInDistrictById(districtId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ward 1", result.get(0).getName());
        assertEquals("Ward 2", result.get(1).getName());
        verify(wardRepository, times(1)).findWardsInDistrict(districtId);
    }

    @Test
    public void testFindWardIdAndNameById_WardFound_GetSingleResult() {
        Integer wardId = 1;
        Ward ward = new Ward(wardId, "Ward 1");

        when(wardRepository.findWardIdAndNameById(wardId)).thenReturn(ward);

        Ward result = addressService.findWardIdAndNameById(wardId);

        assertNotNull(result);
        assertEquals(wardId, result.getId());
        assertEquals("Ward 1", result.getName());
        verify(wardRepository, times(1)).findWardIdAndNameById(wardId);
    }

    @Test
    public void CreateAddress_WardNotFound_NotFoundExceptionThrown() {
        String streetAddress = "123 Ta Quang Buu";
        Integer wardId = 1;
        Integer districtId = 2; // Not used in the current method but included for completeness
        Integer provinceId = 3; // Not used in the current method but included for completeness

        when(wardRepository.findById(wardId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            addressService.createAddress(streetAddress, wardId, districtId, provinceId);
        });

        assertEquals("Ward not found: " + wardId, exception.getMessage());
        verify(wardRepository, times(1)).findById(wardId);
        verify(addressRepository, times(0)).save(any(Address.class));
    }

    @Test
    public void testCreateAddress_WardExists() {
        String streetAddress = "123 Ta Quang Buu";
        Integer wardId = 1;
        Integer districtId = 2; // Not used in the current method but included for completeness
        Integer provinceId = 3; // Not used in the current method but included for completeness

        Ward ward = new Ward();
        ward.setId(wardId);

        when(wardRepository.findById(wardId)).thenReturn(Optional.of(ward));

        Address address = new Address(streetAddress);
        address.setWard(ward);

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address result = addressService.createAddress(streetAddress, wardId, districtId, provinceId);

        assertNotNull(result);
        assertEquals(streetAddress, result.getName());
        assertEquals(ward, result.getWard());
        verify(wardRepository, times(1)).findById(wardId);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

}
