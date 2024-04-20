package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Repository.AddressRepository;
import nsv.com.nsvserver.Repository.ProfileDao;
import org.springframework.transaction.annotation.Transactional;

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

    private AddressService addressService;

    private AddressRepository addressRepository;

    private ProfileDao profileDaoImpl;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ProfileRepository profileRepository, AddressService addressService, AddressRepository addressRepository, ProfileDao profileDaoImpl) {
        this.employeeRepository = employeeRepository;
        this.profileRepository = profileRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.profileDaoImpl = profileDaoImpl;
    }

    public PageDto getAllEmployeeProfile(Integer page, Integer pageSize){
        System.out.println("getAllEmployeeProfile");
        List<Profile> profiles = profileDaoImpl.findAllWithEagerLoading(page, pageSize);
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
        if (address!=null)
            addressRepository.delete(address);
        profile.setAddress(null);

        address = addressService.createAddress(
                addressDtos.getAddress(), addressDtos.getWardId(),
                addressDtos.getDistrictId(), addressDtos.getProvinceId());

        Profile mergedProfile = new Profile(profileDto,profile.getId());
        mergedProfile.setAddress(address);
        address.setProfile(mergedProfile);
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
        return createEmployeeDto(employee.getProfile());
    }



    public EmployeeDto createEmployeeDto(Profile profile){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(profile.getName());
        employeeDto.setPhoneNumber(profile.getPhoneNumber());
        employeeDto.setEmail(profile.getEmail());
        employeeDto.setGender(profile.getGender());
        employeeDto.setStatus(profile.getEmployee().getStatus());
        Address address = profile.getAddress();
        employeeDto.setAddresses(address);
        employeeDto.setRoles(profile.getEmployee().getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return employeeDto;
    }


}
