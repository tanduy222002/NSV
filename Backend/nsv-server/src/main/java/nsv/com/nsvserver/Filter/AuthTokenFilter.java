package nsv.com.nsvserver.Filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsv.com.nsvserver.Entity.EmployeeDetail;
import nsv.com.nsvserver.Service.EmployeeDetailService;
import nsv.com.nsvserver.Service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    private EmployeeDetailService employeeDetailService;


    private HandlerExceptionResolver resolver;
    @Autowired
    public AuthTokenFilter(JwtTokenService jwtTokenService, EmployeeDetailService employeeDetailService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtTokenService = jwtTokenService;
        this.employeeDetailService = employeeDetailService;
        this.resolver = resolver;
    }



    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = parseJwt(request);

            if (jwt != null && jwtTokenService.validateToken(jwt)) {
                String userName = jwtTokenService.getUserNameFromJWT(jwt);

                EmployeeDetail employeeDetail = employeeDetailService.loadUserByUsername(userName);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                employeeDetail,
                                null,
                                employeeDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception e) {
            System.out.println("The message:" + e.getCause() + " is: " + e.getMessage());
            resolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);


    }
}
