package istic.m2.project.gofback.exceptions;

import istic.m2.project.gofback.config.AppConfig;

public class ErrorUtils {

    public static BusinessException throwBusnessException(MessageError messageError, String msg) {
        return new BusinessException(messageError, msg);
    }

    public static void verifyPaginationIndex(Integer begin, Integer end, AppConfig appConfig) throws BusinessException {
        if (begin > end) {
            throw new BusinessException(MessageError.PAGINATION_ERROR, "begin can't be greater than end");
        }
        if ((end - begin) + 1 > appConfig.getPaginationDefaultPageSize()) {
            throw new BusinessException(MessageError.PAGINATION_ERROR, String.format("Page size can't be greater than %s",
                    appConfig.getPaginationDefaultPageSize()));
        }
        if ((end.equals(begin))) {
            throw new BusinessException(MessageError.PAGINATION_ERROR, "begin and end can't be equals");
        }
    }
}
