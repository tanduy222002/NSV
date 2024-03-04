package nsv.com.nsvserver.ExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Autowired @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
//        body.put("message", authException.getMessage());
//        body.put("path", request.getServletPath());
//        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(), body);
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
        resolver.resolveException(request, response, null, authException);
    }


}
