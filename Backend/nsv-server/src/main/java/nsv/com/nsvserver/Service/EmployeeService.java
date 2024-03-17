package nsv.com.nsvserver.Service;

import org.springframework.transaction.annotation.Transactional;
import nsv.com.nsvserver.Dto.EmployeeDto;

import nsv.com.nsvserver.Dto.ProfileDto;
import nsv.com.nsvserver.Entity.*;

import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    private ProfileRepository profileRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ProfileRepository profileRepository) {
        this.employeeRepository = employeeRepository;
        this.profileRepository = profileRepository;
    }


    public List<EmployeeDto> getAllEmployee(){
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for(Employee employee : employeeList){
            EmployeeDto employeeDto = createEmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    @Transactional
    public void updateEmployeeProfile(Integer id, ProfileDto employeeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetail employeeDetail = (EmployeeDetail) authentication.getPrincipal();
        if(employeeDetail.getEmployee().getId() != id)   {
            throw new IllegalArgumentException("The employee id is not match employee account");
        }
        Profile profile = employeeDetail.getEmployee().getProfile();
        Profile mergedProfile = new Profile(employeeDto,profile.getId());
        profileRepository.save(mergedProfile);
    }

    @Transactional
    public void deleteEmployee(Integer id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Employee with id: " + id + " not found");
        }


    }

    public EmployeeDto getEmployeeById(Integer id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee  = employeeOptional.orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));
        return createEmployeeDto(employee);
    }



    public EmployeeDto createEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employee.getProfile().getName());
        employeeDto.setPhoneNumber(employee.getProfile().getPhoneNumber());
        employeeDto.setEmail(employee.getProfile().getEmail());
        employeeDto.setGender(employee.getProfile().getGender());
        employeeDto.setStatus(employee.getStatus());
        employeeDto.setAddresses(employee.getProfile().getAddresses().stream().map(Address::getName).collect(Collectors.toList()));
        employeeDto.setRoles(employee.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return employeeDto;
    }


}
