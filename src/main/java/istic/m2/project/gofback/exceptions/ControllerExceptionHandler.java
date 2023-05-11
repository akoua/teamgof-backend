package istic.m2.project.gofback.exceptions;

import istic.m2.project.gofback.controllers.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BUSSINESS_EXCEPTION = "BUSSINESS EXCEPTION";

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

}
