package nsv.com.nsvserver.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.EmployeeDetail;
import nsv.com.nsvserver.Entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Component
@Slf4j
public class JwtTokenService {
    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;
//    private final long JWT_EXPIRATION = 1L;



    // Tạo ra jwt từ thông tin user
    public String generateToken(Employee employee) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        List<String> roles = employee.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        // Tạo chuỗi json web token từ username của user.
        return Jwts.builder()
                .setSubject(employee.getUserName())
                .setIssuedAt(now)
                .claim("roles", roles)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJWT(String token) {

            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
                String userName = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build().parseSignedClaims(token)
                    .getPayload().getSubject();

            return userName;
    }





    public boolean validateToken(String authToken) throws ServletException {
        try {
            System.out.println("Try to validate");
            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
            System.out.println(key);
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build().parseSignedClaims(authToken);

            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        } catch(Exception e){
            System.out.println("Exception");

        }
        return false;
    }
}