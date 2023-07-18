package ru.dimax.main.exception;

public class ErrorResponse {
    private final String status;
    private final String reason;
    private final String description;
    private final String timestamp;

    public ErrorResponse(String status, String reason, String description, String timestamp) {
        this.status = status;
        this.reason = reason;
        this.description = description;
        this.timestamp = timestamp;
    }


}
