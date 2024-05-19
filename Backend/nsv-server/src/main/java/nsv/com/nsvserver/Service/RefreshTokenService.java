package nsv.com.nsvserver.Service;


import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.RefreshToken;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.RefreshTokenExpiredException;
import nsv.com.nsvserver.Repository.EmployeeRepository;
import nsv.com.nsvserver.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;
    private EmployeeRepository employeeRepository;


    private JwtTokenService jwtTokenService;
    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long REFRESH_TOKEN_EXPIRATION;
    @Autowired
    public RefreshTokenService(JwtTokenService jwtTokenService,RefreshTokenRepository refreshTokenRepository, EmployeeRepository employeeRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.employeeRepository = employeeRepository;
        this.jwtTokenService=jwtTokenService;
    }
    @Transactional
    public RefreshToken generateRefreshToken(Employee employee) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());

        Date now = new Date();
        refreshToken.setExpiryDate(
                new Date(now.getTime()+REFRESH_TOKEN_EXPIRATION)
        );

        employee.setRefreshToken(refreshToken);

        employeeRepository.save(employee);

        return refreshToken;
    }

    public String refreshToken (String refreshTokenString){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenString).orElseThrow(
                ()->new NotFoundException("Refresh token: " + refreshTokenString + " not found in database" )
        );

        if(refreshToken.getExpiryDate().getTime()<System.currentTimeMillis()){
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException();
        }

        String token = jwtTokenService.generateToken(refreshToken.getEmployee());

        return token;

    }


}
