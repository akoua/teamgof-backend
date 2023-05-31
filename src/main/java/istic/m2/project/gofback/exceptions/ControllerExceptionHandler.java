package istic.m2.project.gofback.exceptions;

import istic.m2.project.gofback.controllers.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BUSSINESS_EXCEPTION = "BUSSINESS EXCEPTION";
    private static final String DATABASE_EXCEPTION = "DATABASE EXCEPTION";
    private static final String AUTHENTICATION_EXCEPTION = "AUTHENTICATION EXCEPTION";

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleBusinessException(BusinessException ex) {
        log.error(BUSSINESS_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                ex.getMessageCode(), ex.getContext()));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleDataIntegrationException(DataIntegrityViolationException ex) {
        log.error(DATABASE_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                MessageError.ERROR_DATABASE, cutMessage(ex.getRootCause().getMessage(), "Key.*")));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error(AUTHENTICATION_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                MessageError.AUTH_ERROR, ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(AUTHENTICATION_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                MessageError.AUTH_ERROR, ex.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleDataIntegrationException(InvalidDataAccessApiUsageException ex) {
        log.error(DATABASE_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                MessageError.ERROR_DATABASE, cutMessage(ex.getMessage(), "([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)")));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    private String cutMessage(String msg, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(msg);
        String ret = msg;

        while (matcher.find()) {
            ret = matcher.group();
        }
        return ret;
    }

}
