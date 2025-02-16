package org.education.spring_security_demo.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessage message = ErrorMessage.builder()
                .message("Доступ запрещен: " + ex.getMessage())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .message("Ошибка аутентификации: " + ex.getMessage())
                .description(request.getDescription(false))
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .message("Логин или пароль не корректные: " + ex.getMessage())
                .description(request.getDescription(false))
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .statusCode(HttpStatus.FOUND.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.FOUND);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}
