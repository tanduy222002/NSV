package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Annotation.TraceTime;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.EmployeeDetail;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailService implements UserDetailsService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeDetailService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @TraceTime
    public EmployeeDetail loadUserByUsername(String userName) {
        // Kiểm tra xem user có tồn tại trong database không?

        Employee employee = employeeRepository.findByUserName(userName);
        if (employee == null) {
            throw new UsernameNotFoundException("The user name is not found: " + userName);
        }
        return new EmployeeDetail(employee);
    }

    public static Employee getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof EmployeeDetail) {
            EmployeeDetail employeeDetail= (EmployeeDetail) authentication.getPrincipal();
            return employeeDetail.getEmployee();
        }

        return null;
    }
}
