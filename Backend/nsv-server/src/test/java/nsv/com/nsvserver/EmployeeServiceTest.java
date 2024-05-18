package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.AddressDto;
import nsv.com.nsvserver.Dto.EmployeeDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Dto.ProfileDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.ProfileDao;
import nsv.com.nsvserver.Repository.ProfileRepository;
import nsv.com.nsvserver.Repository.RoleRepository;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.EmployeeDetailService;
import nsv.com.nsvserver.Service.EmployeeService;
import nsv.com.nsvserver.Util.ConvertUtil;
import nsv.com.nsvserver.Util.EmployeeRoles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private ProfileDao profileDaoImpl;

    @InjectMocks
    private EmployeeService employeeService;
    @Test
    public void updateEmployeeStatus_Success() {
        Integer employeeId = 1;
        String newStatus = "ACTIVE";

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setStatus("SUSPENDED");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEmployeeStatus(employeeId, newStatus);

        assertEquals(newStatus, employee.getStatus());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void updateEmployeeStatus_EmployeeNotFound_NotFoundExceptionThrown() {
        Integer employeeId = 1;
        String newStatus = "Active";

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.updateEmployeeStatus(employeeId, newStatus));

        verify(employeeRepository, times(1)).findById(employeeId);

    }

    @Test
    public void testDeleteEmployee_Success() {
        Integer employeeId = 1;

        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    public void testDeleteEmployee_EmployeeNotFound_NotFoundExceptionThrown() {
        Integer employeeId = 1;

        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(employeeId));

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(0)).deleteById(employeeId);
    }

    @Test
    public void getEmployeeById_Success() {
        Integer employeeId = 1;
        Integer profileId = 1;
        Employee employee = new Employee("","");
        employee.setId(employeeId);
        Role role = new Role("ROLE_EMPLOYEE");
        employee.addRole(role);
        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setEmployee(employee);

        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.of(profile));

        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);

        assertNotNull(employeeDto);
        assertEquals(employeeId, employeeDto.getEmployeeId());
        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
    }

    @Test
    public void testGetEmployeeById_EmployeeNotFound_NotFoundExceptionThrown() {
        Integer employeeId = 1;

        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.getEmployeeById(employeeId));

        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
    }




    @Test
    public void testCreateEmployeeDto() {
        // Create mock data
        Address address = new Address();
        address.setName("Street");
        Ward ward = new Ward();
        ward.setName("ward");
        address.setWard(ward);
        District district = new District();
        district.setName("district");
        ward.setDistrict(district);
        Province province = new Province();
        province.setName("province");
        district.setProvince(province);


        Role role = new Role();
        role.setName("ROLE_USER");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setStatus("ACTIVE");
        employee.setRoles(Arrays.asList(role));

        Profile profile = new Profile();
        profile.setName("profile");
        profile.setPhoneNumber("123-456-7890");
        profile.setEmail("profile@test.com");
        profile.setGender("M");
        profile.setAddress(address);
        profile.setEmployee(employee);


        // Call the method under test
        EmployeeDto employeeDto = employeeService.createEmployeeDto(profile);

        // Verify the result
        assertEquals("profile", employeeDto.getName());
        assertEquals("123-456-7890", employeeDto.getPhoneNumber());
        assertEquals("profile@test.com", employeeDto.getEmail());
        assertEquals("M", employeeDto.getGender());
        assertEquals("ACTIVE", employeeDto.getStatus());
        assertEquals("Street, ward, district, province", employeeDto.getAddress());
        assertEquals(Arrays.asList("ROLE_USER"), employeeDto.getRoles());
        assertEquals(1, employeeDto.getEmployeeId());
    }

    @Test
    public void testGetAllEmployeeProfile() {
        // Arrange
        Profile profile1 = new Profile();
        profile1.setName("profile1");
        profile1.setEmail("profile1@test.com");
        profile1.setPhoneNumber("123456789");
        Employee employee1 = new Employee();
        employee1.setStatus("active");
        profile1.setEmployee(employee1);

        Profile profile2 = new Profile();
        profile2.setName("profile2");
        profile2.setEmail("profile2@test.com");
        profile2.setPhoneNumber("123456789");
        Employee employee2 = new Employee();
        employee2.setStatus("active");
        profile2.setEmployee(employee2);

        List<Profile> profiles = Arrays.asList(profile1, profile2);

        when(profileDaoImpl.findAllWithEagerLoading(1, 2, null , "active"))
                .thenReturn(profiles);
        when(profileDaoImpl.getTotalCount())
                .thenReturn(2L);

        // Act
        PageDto result = employeeService.getAllEmployeeProfile(1, 2, null, "active");

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getTotalElement());
        assertEquals(1.0, result.getTotalPage());
        assertEquals(1, result.getPage());
        assertEquals(2, result.getContent().size());

        EmployeeDto employeeDto1 = (EmployeeDto) result.getContent().get(0);
        assertEquals("profile1", employeeDto1.getName());
        assertEquals("profile1@test.com", employeeDto1.getEmail());
        assertEquals("123456789", employeeDto1.getPhoneNumber());

        EmployeeDto employeeDto2 = (EmployeeDto) result.getContent().get(1);
        assertEquals("profile2", employeeDto2.getName());
        assertEquals("profile2@test.com", employeeDto2.getEmail());
        assertEquals("123456789", employeeDto2.getPhoneNumber());

    }

    @Test
    public void updateEmployeeRole_EmployeeNotFound_NotFoundExceptionThrown() {
        // Arrange
        Integer employeeId = 1;
        EmployeeRoles e = EmployeeRoles.ROLE_EMPLOYEE;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,()->{
            employeeService.updateEmployeeRole(employeeId, e);
        });

    }
    @Test
    public void testUpdateEmployeeRole() {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();

        employee.setId(employeeId);
        Role currentRole = new Role();
        currentRole.setName("ROLE_EMPLOYEE");
        employee.addRole(currentRole);


        EmployeeRoles e  = EmployeeRoles.ROLE_MANAGER;
        Role newRole = new Role();
        newRole.setName("ROLE_MANAGER");


        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(roleRepository.findByName("ROLE_MANAGER")).thenReturn(newRole);

        // Act
        employeeService.updateEmployeeRole(employeeId, e);

        // Assert
        verify(employeeRepository).findById(employeeId);
        verify(roleRepository).findByName(e.name());

        assertTrue(employee.getRoles().contains(newRole));
        assertFalse(employee.getRoles().contains(currentRole));
    }


    @Test
    public void updateEmployeeProfile2() {
        Profile profile = new Profile();
        profile.setEmail("profile@test.com");

        Address address = new Address();
        profile.setAddress(address);

        Employee employee = new Employee();
        employee.setProfile(profile);

        ProfileDto profileDto = new ProfileDto();
        profileDto.setName("New Name");
        profileDto.setAddresses(null);
        // Arrange
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        // Act
        employeeService.updateEmployeeProfile(1, profileDto);

        // Assert
        verify(employeeRepository).findById(1);


        assertEquals("New Name", profile.getName());
//        assertEquals("Male", profile.getGender());
    }
    @Test
    public void testUpdateEmployeeProfile() {
        Profile profile = new Profile();
        profile.setEmail("profile@test.com");

        Address address = new Address();
        profile.setAddress(address);

        Employee employee = new Employee();
        employee.setProfile(profile);

        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("New Address");
        addressDto.setWardId(1);
        addressDto.setDistrictId(1);
        addressDto.setProvinceId(1);

        ProfileDto profileDto = new ProfileDto();
        profileDto.setName("New Name");
        profileDto.setGender("Male");
        profileDto.setEmail("new@test.com");
        profileDto.setPhoneNumber("123456789");
        profileDto.setAddresses(addressDto);
        // Arrange
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(profileRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(addressService.createAddress(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(new Address());

        // Act
        employeeService.updateEmployeeProfile(1, profileDto);

        // Assert
        verify(employeeRepository).findById(1);
        verify(addressService).createAddress(
                addressDto.getAddress(), addressDto.getWardId(),
                addressDto.getDistrictId(), addressDto.getProvinceId());
        verify(profileRepository).existsByEmail("new@test.com");

        assertEquals("New Name", profile.getName());
        assertEquals("Male", profile.getGender());
        assertEquals("new@test.com", profile.getEmail());
        assertEquals("123456789", profile.getPhoneNumber());
    }


}