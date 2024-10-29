package br.com.gubee.patterns.circuitbreaker;

public interface CircuitBreaker {

    void requestFailed();

    void successRequest();

    boolean isClosed();

    boolean isHalfOpen();

    void cleanFails();

    void cleanSuccess();

    void fail();
}
