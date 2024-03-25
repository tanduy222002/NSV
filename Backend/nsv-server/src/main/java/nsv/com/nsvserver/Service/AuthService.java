package nsv.com.nsvserver.Service;


import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Dto.AuthResponseDto;
import nsv.com.nsvserver.Dto.SignUpRequestDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.UserNameExistsException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.RefreshTokenRepository;
import nsv.com.nsvserver.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    AuthenticationManager authenticationManager;

    EmployeeRepository employeeRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    JwtTokenService jwtTokenService;

    RefreshTokenRepository refreshTokenRepository;

    RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtTokenService jwtTokenService, RefreshTokenRepository refreshTokenRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponseDto authenticateUser (String userName, String password){
        System.out.println("authenticateUser");
        System.out.println(userName);
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        EmployeeDetail employeeDetail = (EmployeeDetail) authentication.getPrincipal();
        Employee employee = employeeDetail.getEmployee();
        String jwt = jwtTokenService.generateToken(employee);

        if(employee.getRefreshToken()==null){
            RefreshToken refreshToken = refreshTokenService.generateRefreshToken(employee);
        }

        List<String> roles = employee.getRoles().stream()
                .map(item -> item.getName())
                .collect(Collectors.toList());
        return new AuthResponseDto(employee.getId(),jwt,roles,"Create successfully",employee.getRefreshToken().getToken());
    }
    @Transactional
    public AuthResponseDto signUp(SignUpRequestDto signUpRequestDto){
        //Get request information
        String userName = signUpRequestDto.getUserName();
        String password = signUpRequestDto.getPassword();
        String email= signUpRequestDto.getEmail();
        List<String> roles = signUpRequestDto.getRoles();

        //Check if username is already existed
        if (employeeRepository.existsByUserName(userName)){
            throw new UserNameExistsException("Username already exists");
        }

        // Create new employee
        Employee employee =new Employee(userName, encoder.encode(password));

        //Set roles for employee
        if(roles==null || roles.isEmpty()){
            roles =Arrays.asList("ROLE_EMPLOYEE");
        }
        else{
            checkValidRole(roles);
        }
        List<Role> authorization = new ArrayList<>();
        for(String role : roles){
            Optional<Role> optionalValue = Optional.ofNullable(roleRepository.findByName(role));
            Role result = optionalValue.orElseGet(()->{
                Role newRole = new Role(role);
                roleRepository.save(newRole);
                return newRole;
            });
            authorization.add(result);
        }
        employee.setRoles(authorization);

        //set employee status
        employee.setStatus("ACTIVE");

        //Create profile for employee
        Profile employeeProfile = new Profile();
        employeeProfile.setName(userName);
        employeeProfile.setEmail(email);
        employee.setProfile(employeeProfile);

        //Create refreshToken and Insert employee into db
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(employee);


        //generate jwt token and return DTO used for response
        String jwt = jwtTokenService.generateToken(employee);
        return new AuthResponseDto(employee.getId(),jwt,roles,"Signup successfully",refreshToken.getToken());

    }

    @Transactional
    public void logOut(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetail employeeDetail = (EmployeeDetail) authentication.getPrincipal();
        Employee employee = employeeDetail.getEmployee();
        refreshTokenRepository.deleteByEmployee(employee);
    }
    public void checkValidRole(List<String> roles){
        List<String> acceptedRole = Arrays.asList("ROLE_EMPLOYEE", "ROLE_ADMIN", "ROLE_MANAGER");
        for (String role : roles){
            if(!acceptedRole.contains(role)){
                throw new RuntimeException("Invalid role: " + role);
            }
        }
    }
}
