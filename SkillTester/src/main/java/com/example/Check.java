package com.example;

/**
 * The check contains the request message and the expected and received response.
 * The passed boolean indicates whether or not the expected and received response match.
 */
public class Check {
    private String request;
    private String expectedResponse;
    private String receivedResponse;
    private boolean passed;

    public Check(String request, String expectedResponse) {
        this.request = request;
        this.expectedResponse = expectedResponse;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getExpectedResponse() {
        return expectedResponse;
    }

    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

    public String getReceivedResponse() {
        return receivedResponse;
    }

    public void setReceivedResponse(String receivedResponse) {
        this.receivedResponse = receivedResponse;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
