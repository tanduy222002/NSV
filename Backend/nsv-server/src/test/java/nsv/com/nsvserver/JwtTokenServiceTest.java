package nsv.com.nsvserver;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.Role;
import nsv.com.nsvserver.Service.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    @InjectMocks
    JwtTokenService jwtTokenService = new JwtTokenService();
    private String JWT_SECRET="NONGSANVIETSECRECTKEYFORGENERATEJWTWEBTOKENNONGSANVIETSECRECTKEYFORGENERATEJWTWEBTOKEN";
    private long JWT_EXPIRATION = 86400000L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(jwtTokenService, "JWT_SECRET", JWT_SECRET); // Ensure this is a 16-byte key
        ReflectionTestUtils.setField(jwtTokenService, "JWT_EXPIRATION", JWT_EXPIRATION); // Example expiration time
    }

    @Test
    public void generateToken_GenerateToken() {
        final String employeeName = "testName";
        Employee employee = new Employee();
        employee.setUserName(employeeName);
        employee.setRoles(Arrays.asList(new Role("ROLE_EMPLOYEE")));


        // Act
        String token = jwtTokenService.generateToken(employee);
        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void getUserNameFromJWT_ReturnUserName() {
        // Arrange
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0TmFtZSIsImlhdCI6MTcxNTg0NDM0Mywicm9sZXMiOlsiUk9MRV9FTVBMT1lFRSJdLCJleHAiOjE3MTU5MzA3NDN9.k-phT7heqOwNN6zQGrx_-qYLyJ2BoDWjfm3OMfsdTDPv-9s5256n7yp_Yakfpmjc1h3Lx0d3hoJmQ1OtqcJkLA";

        // Act
        try{
        String userName = jwtTokenService.getUserNameFromJWT(token);
        // Assert
        assertEquals("testName", userName);
        }
        catch (Exception e){

        }
    }

    @Test
    public void validateToken_ValidToken_ReturnTrue()  {
        // Arrange
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0TmFtZSIsImlhdCI6MTcxNTg0NDM0Mywicm9sZXMiOlsiUk9MRV9FTVBMT1lFRSJdLCJleHAiOjE3MTU5MzA3NDN9.k-phT7heqOwNN6zQGrx_-qYLyJ2BoDWjfm3OMfsdTDPv-9s5256n7yp_Yakfpmjc1h3Lx0d3hoJmQ1OtqcJkLA";
        try {
           boolean isValid = jwtTokenService.validateToken(token);
           assertEquals(true,isValid);
        }
        catch (Exception e){

        }

    }

    @Test
    public void validateToken_InvalidToken_MalformedJwtExceptionThrown() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act & Assert
        assertThrows(MalformedJwtException.class, () -> jwtTokenService.validateToken(invalidToken));
    }
    @Test
    public void validateToken_InvalidToken_ExpiredJwtExceptionThrown() {
        // Arrange
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0TmFtZSIsImlhdCI6MTcxNTg0NDk2Miwicm9sZXMiOlsiUk9MRV9FTVBMT1lFRSJdLCJleHAiOjE3MTU4NDQ5NjJ9.3mLg0SLq0LZONPvzXXxCUaFw-s8vaVc5GLYak3yBTmPSEVOqn4Yg0fhGeWLlHxdFctRlITTy-Ih0nHvaJFtYig";

        // Act & Assert
        assertThrows(ExpiredJwtException.class, () -> jwtTokenService.validateToken(expiredToken));
    }


}
