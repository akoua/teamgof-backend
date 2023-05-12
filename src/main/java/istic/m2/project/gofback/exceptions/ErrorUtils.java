package istic.m2.project.gofback.exceptions;

public class ErrorUtils {

    public static BusinessException throwBusnessException(MessageError messageError, String msg) {
        return new BusinessException(messageError, msg);
    }
}
