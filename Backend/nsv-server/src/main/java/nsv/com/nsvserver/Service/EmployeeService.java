package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Util.ConvertUtil;
import nsv.com.nsvserver.Util.EmployeeRoles;
import org.springframework.transaction.annotation.Transactional;

import nsv.com.nsvserver.Entity.*;

import nsv.com.nsvserver.Exception.NotFoundException;
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
    private final RoleRepository roleRepository;
    private EmployeeRepository employeeRepository;

    private ProfileRepository profileRepository;

    private AddressService addressService;

    private AddressRepository addressRepository;

    private ProfileDao profileDaoImpl;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ProfileRepository profileRepository, AddressService addressService, AddressRepository addressRepository, ProfileDao profileDaoImpl, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.profileRepository = profileRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.profileDaoImpl = profileDaoImpl;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void updateEmployeeRole(Integer id, EmployeeRoles role){
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Employee not found with id: "+id));
        List<Role> roles = employee.getRoles();
        Role current = roles.get(0);
        Role newRole = roleRepository.findByName(role.name());
        employee.removeRole(current);
        employee.addRole(newRole);
    }

    @Transactional
    public void updateEmployeeStatus(Integer id, String status){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new NotFoundException("No employee found for id " + id));
        employee.setStatus(status);
    }
    public PageDto getAllEmployeeProfile(Integer page, Integer pageSize, String name, String status){
        System.out.println("getAllEmployeeProfile");
        List<Profile> profiles = profileDaoImpl.findAllWithEagerLoading(page, pageSize,name,status);
        System.out.println("before mapping");
        List<EmployeeDto> results = profiles.stream().map(profileDto -> createEmployeeDto(profileDto)).collect(Collectors.toList());
        long totalCount = profileDaoImpl.getTotalCount();
        return new PageDto(Math.ceil((double) totalCount / pageSize), totalCount, page, results);
    }

    @Transactional
    public void updateEmployeeProfile(Integer id, ProfileDto profileDto) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new NotFoundException("Employee not found"));
        Profile profile = employee.getProfile();
        AddressDto addressDtos = profileDto.getAddresses();
        Address address=profile.getAddress();

        if(addressDtos!=null){
            profile.setAddress(null);
            address = addressService.createAddress(
                    addressDtos.getAddress(), addressDtos.getWardId(),
                    addressDtos.getDistrictId(), addressDtos.getProvinceId());
        }

        if(profileDto.getName()!=null){
            profile.setName(profileDto.getName());
        }
        if(profileDto.getGender()!=null){
            profile.setGender(profileDto.getGender());
        }
        if(profileDto.getEmail()!=null){
            if(profileRepository.existsByEmail(profileDto.getEmail())
                    &&!profileDto.getEmail().equals(profile.getEmail())){
                throw new ExistsException("Email already taken");
            }
            profile.setEmail(profileDto.getEmail());
        }
        if(profileDto.getPhoneNumber()!=null){
            profile.setPhoneNumber(profileDto.getPhoneNumber());
        }


    }

    @Transactional
    public void deleteEmployee(Integer id){
        if(employeeRepository.existsById(id)){
            System.out.println("Before delete");
            employeeRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Employee with id: " + id + " not found");
        }


    }

    public EmployeeDto getEmployeeById(Integer id){
        Profile profile = profileDaoImpl.findOneWithEagerLoading(id).orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));

        return createEmployeeDto(profile);
    }


//    @Transactional
//    public void updateEmployeeStatus(Integer id){
//        Profile profile = profileDaoImpl.findOneWithEagerLoading(id).orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));
//        Employee employee = profile.getEmployee();
//
//    }



    public EmployeeDto createEmployeeDto(Profile profile){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(profile.getName());
        employeeDto.setPhoneNumber(profile.getPhoneNumber());
        employeeDto.setEmail(profile.getEmail());
        employeeDto.setGender(profile.getGender());
        employeeDto.setStatus(profile.getEmployee().getStatus());
        Address address = profile.getAddress();
        if(address != null){
            employeeDto.setAddresses(address);
            employeeDto.setAddress(ConvertUtil.convertAddressToString(address));
        }

        employeeDto.setRoles(profile.getEmployee().getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        employeeDto.setEmployeeId(profile.getEmployee().getId());

        return employeeDto;
    }


}
