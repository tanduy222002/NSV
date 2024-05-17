package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.EmployeeDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.ProfileDao;
import nsv.com.nsvserver.Repository.ProfileRepository;
import nsv.com.nsvserver.Repository.RoleRepository;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.EmployeeDetailService;
import nsv.com.nsvserver.Service.EmployeeService;
import nsv.com.nsvserver.Util.ConvertUtil;
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
//    @Test
//    public void testUpdateEmployeeStatus_Success() {
//        Integer employeeId = 1;
//        String newStatus = "Active";
//
//        Employee employee = new Employee();
//        employee.setId(employeeId);
//        employee.setStatus("Inactive");
//
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
//
//        employeeService.updateEmployeeStatus(employeeId, newStatus);
//
//        assertEquals(newStatus, employee.getStatus());
//        verify(employeeRepository, times(1)).findById(employeeId);
//        verify(employeeRepository, times(1)).save(employee);
//    }
//
//    @Test
//    public void testUpdateEmployeeStatus_EmployeeNotFound() {
//        Integer employeeId = 1;
//        String newStatus = "Active";
//
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> employeeService.updateEmployeeStatus(employeeId, newStatus));
//
//        verify(employeeRepository, times(1)).findById(employeeId);
//        verify(employeeRepository, times(0)).save(any(Employee.class));
//    }
//    public void testDeleteEmployee_Success() {
//        Integer employeeId = 1;
//
//        when(employeeRepository.existsById(employeeId)).thenReturn(true);
//
//        employeeService.deleteEmployee(employeeId);
//
//        verify(employeeRepository, times(1)).existsById(employeeId);
//        verify(employeeRepository, times(1)).deleteById(employeeId);
//    }
//
//    @Test
//    public void testDeleteEmployee_EmployeeNotFound() {
//        Integer employeeId = 1;
//
//        when(employeeRepository.existsById(employeeId)).thenReturn(false);
//
//        assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
//
//        verify(employeeRepository, times(1)).existsById(employeeId);
//        verify(employeeRepository, times(0)).deleteById(employeeId);
//    }
//
//    @Test
//    public void testGetEmployeeById_Success() {
//        Integer employeeId = 1;
//        Profile profile = new Profile();
//        profile.setId(employeeId);
//
//        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.of(profile));
//
//        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
//
//        assertNotNull(employeeDto);
//        assertEquals(employeeId, employeeDto.getId());
//        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
//    }
//
//    @Test
//    public void testGetEmployeeById_EmployeeNotFound() {
//        Integer employeeId = 1;
//
//        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
//
//        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
//    }
//
//    @Test
//    public void testUpdateEmployeeStatus_Success() {
//        Integer employeeId = 1;
//        Profile profile = new Profile();
//        profile.setId(employeeId);
//        Employee employee = new Employee();
//        profile.setEmployee(employee);
//
//        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.of(profile));
//
//        employeeService.updateEmployeeStatus(employeeId);
//
//        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
//        // Add additional verifications if the updateEmployeeStatus method performs any actions on the employee object
//    }
//
//    @Test
//    public void testUpdateEmployeeStatus_EmployeeNotFound() {
//        Integer employeeId = 1;
//
//        when(profileDaoImpl.findOneWithEagerLoading(employeeId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> employeeService.updateEmployeeStatus(employeeId));
//
//        verify(profileDaoImpl, times(1)).findOneWithEagerLoading(employeeId);
//    }
//    @Test
//    public void testCreateEmployeeDto() {
//        // Create mock data
//        Address address = new Address();
//        address.setStreet("123 Main St");
//        address.setCity("City");
//        address.setState("State");
//        address.setZip("12345");
//
//        Role role = new Role();
//        role.setName("ROLE_USER");
//
//        Employee employee = new Employee();
//        employee.setId(1);
//        employee.setStatus("ACTIVE");
//        employee.setRoles(Arrays.asList(role));
//
//        Profile profile = new Profile();
//        profile.setName("John Doe");
//        profile.setPhoneNumber("123-456-7890");
//        profile.setEmail("john.doe@example.com");
//        profile.setGender("Male");
//        profile.setAddress(address);
//        profile.setEmployee(employee);
//
//        // Optionally mock the ConvertUtil method
//        when(ConvertUtil.convertAddressToString(address)).thenReturn("123 Main St, City, State, 12345");
//
//        // Call the method under test
//        EmployeeDto employeeDto = employeeService.createEmployeeDto(profile);
//
//        // Verify the result
//        assertEquals("John Doe", employeeDto.getName());
//        assertEquals("123-456-7890", employeeDto.getPhoneNumber());
//        assertEquals("john.doe@example.com", employeeDto.getEmail());
//        assertEquals("Male", employeeDto.getGender());
//        assertEquals("ACTIVE", employeeDto.getStatus());
//        assertEquals("123 Main St, City, State, 12345", employeeDto.getAddress());
//        assertEquals(Arrays.asList("ROLE_USER"), employeeDto.getRoles());
//        assertEquals(1, employeeDto.getEmployeeId());
//    }


}