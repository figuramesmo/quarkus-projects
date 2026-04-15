package ada.caixa.errorhandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int status;
    @JsonProperty("when")
    private LocalDateTime currentDateTime;

    // Constructors, getters, and setters
    public ErrorResponse(String message, int status, LocalDateTime currentDateTime) {
        this.message = message;
        this.status = status;
        this.currentDateTime = currentDateTime;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }
}
