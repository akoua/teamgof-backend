package istic.m2.project.gofback.exceptions;

import istic.m2.project.gofback.controllers.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
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
                MessageError.ERROR_DATABASE, ex.getMessage()));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<Serializable>> handleDataIntegrationException(InvalidDataAccessApiUsageException ex) {
        log.error(DATABASE_EXCEPTION, ex);

        ResponseDto<Serializable> body = new ResponseDto<>();
        body.setSuccess(false);
        body.setError(new ResponseDto.MessageDto(
                MessageError.ERROR_DATABASE, cutMessage(ex.getMessage())));
        return ResponseEntity.unprocessableEntity().body(body);
    }

//    @ExceptionHandler({HttpMessageNotWritableException.class})
//    @Order(0)
//    @ResponseBody
//    public ResponseEntity<ResponseDto<Serializable>> handleOtherException(HttpMessageNotWritableException ex) {
//        log.error(DATABASE_EXCEPTION, ex);
//
//        ResponseDto<Serializable> body = new ResponseDto<>();
//        body.setSuccess(false);
//        body.setError(new ResponseDto.MessageDto(
//                MessageError.ERROR_DATABASE, cutMessage(ex.getMessage())));
//        return ResponseEntity.unprocessableEntity().body(body);
//    }

    private String cutMessage(String msg) {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(msg);
        String ret = msg;

        while (matcher.find()) {
            ret = matcher.group();
        }
        return ret;
    }

}
