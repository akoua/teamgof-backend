package istic.m2.project.gofback.exceptions;

import lombok.*;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends Exception {
    private MessageError messageCode;
    private String context;
}
