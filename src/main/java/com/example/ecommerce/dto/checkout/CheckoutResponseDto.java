package com.example.ecommerce.dto.checkout;

public class CheckoutResponseDto {
    private String sessionId;
    private String sessionURL;

    public CheckoutResponseDto(String sessionId, String sessionURL) {
        this.sessionId = sessionId;
        this.sessionURL = sessionURL;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionURL() {
        return sessionURL;
    }

    public void setSessionURL(String sessionURL) {
        this.sessionURL = sessionURL;
    }
}
