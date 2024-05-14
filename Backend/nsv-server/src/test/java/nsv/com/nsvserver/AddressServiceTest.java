package nsv.com.nsvserver;

import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Province;
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

@Profile("test")
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

    @Test()
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

    @Test()
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

}
