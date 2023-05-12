package istic.m2.project.gofback.exceptions;

public enum MessageError {
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found in database"),
    REFRESH_TOKEN_WAS_EXPIRED("Refresh token was expired"),
    CAVALIER_NOT_FOUND("Cavalier not found in database"),
    DISCIPLINE_NOT_FOUND("Discipline not found in database"),
    ERROR_DATABASE("Database error"),
    ERROR_TECHNIQUE("Technical error");
    private String message;

    MessageError(String message) {
        this.message = message;
    }
}
