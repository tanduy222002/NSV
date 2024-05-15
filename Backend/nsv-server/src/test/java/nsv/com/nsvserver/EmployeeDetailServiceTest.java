package nsv.com.nsvserver;

import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.EmployeeDetail;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Service.EmployeeDetailService;
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
public class EmployeeDetailServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    private EmployeeDetailService employeeDetailService;

    @Test
    public void testLoadUserByUsername_UserExists() {
        String userName = "testUser";
        Employee employee = new Employee();
        employee.setUserName(userName);

        when(employeeRepository.findByUserName(userName)).thenReturn(employee);

        EmployeeDetail result = employeeDetailService.loadUserByUsername(userName);

        assertNotNull(result);
        assertEquals(employee, result.getEmployee());
        verify(employeeRepository, times(1)).findByUserName(userName);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String userName = "nonExistentUser";

        when(employeeRepository.findByUserName(userName)).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            employeeDetailService.loadUserByUsername(userName);
        });

        assertEquals("The user name is not found: " + userName, exception.getMessage());
        verify(employeeRepository, times(1)).findByUserName(userName);
    }

    @Test
    public void testGetCurrentUserDetails_UserAuthenticated() {
        Employee employee = new Employee();
        EmployeeDetail employeeDetail = new EmployeeDetail(employee);
        Authentication authentication = new UsernamePasswordAuthenticationToken(employeeDetail, null);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Employee result = EmployeeDetailService.getCurrentUserDetails();

        assertNotNull(result);
        assertEquals(employee, result);
    }

    @Test
    public void testGetCurrentUserDetails_UserNotAuthenticated() {
        Employee employee = new Employee();
        EmployeeDetail employeeDetail = new EmployeeDetail(employee);
        Authentication authentication = new UsernamePasswordAuthenticationToken(employeeDetail, null);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        Employee result = EmployeeDetailService.getCurrentUserDetails();

        assertNull(result);
    }

    @Test
    public void testGetCurrentUserDetails_PrincipalNotEmployeeDetail() {
        Object principal = new Object();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(principal);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Employee result = EmployeeDetailService.getCurrentUserDetails();

        assertNull(result);
    }
}