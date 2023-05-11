package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.exceptions.MessageError;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class ResponseDto<T extends Serializable> implements Serializable {

    @Getter
    private T data;
    private boolean success = true;
    private MessageDto error = null;

    private List<MessageDto> warnings = null;

    public ResponseDto(T data) {
        super();
        this.data = data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageDto implements Serializable {
        private MessageError messageCode;
        private String message;
    }

}
