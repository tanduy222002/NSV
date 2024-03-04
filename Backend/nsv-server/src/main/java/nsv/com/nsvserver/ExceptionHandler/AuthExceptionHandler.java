package nsv.com.nsvserver.ExceptionHandler;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import nsv.com.nsvserver.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AuthExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity(details, HttpStatus.BAD_REQUEST);

    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleIllegalRoleException(IllegalRoleException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleAuthenticateException(AuthenticationException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExpiredToken(ExpiredJwtException e) {
        return new ResponseEntity("Expired JWT Token", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidToken(MalformedJwtException e) {
        return new ResponseEntity("Invalid JWT Token", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUnsupportedToken(UnsupportedJwtException e) {
        return new ResponseEntity("Unsupported JWT Token", HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleIllegalArgumentToken(IllegalArgumentException e) {
        return new ResponseEntity("Illegal JWT Token", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExistsAccount(UserNameExistsException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        System.out.println(e.getClass().getName());
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleProductAlreadyExistException(ProductExistsException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
