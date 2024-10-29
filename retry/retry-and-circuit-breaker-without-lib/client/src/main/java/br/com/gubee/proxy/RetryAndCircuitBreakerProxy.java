package br.com.gubee.proxy;

import br.com.gubee.patterns.circuitbreaker.CircuitBreaker;
import br.com.gubee.patterns.circuitbreaker.CircuitBreakerDefault;
import br.com.gubee.patterns.retry.Retry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RetryAndCircuitBreakerProxy implements InvocationHandler {

    private final Object target;
    private final CircuitBreaker circuitBreaker;


    public RetryAndCircuitBreakerProxy(Object target, CircuitBreaker circuitBreaker) {
        this.target = target;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String resultado = null;

        if (circuitBreaker.isClosed()) {
            try {
                resultado = (String) Retry.retry(() -> method.invoke(target, args), 4, 1500L);
                circuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("Request failed");
                circuitBreaker.fail();
            }
        } else if (circuitBreaker.isHalfOpen()) {
            try {
                resultado = (String) Retry.retry(() -> method.invoke(target, args), 2, 1500L);
                circuitBreaker.successRequest();
                circuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("Request failed");
                circuitBreaker.cleanSuccess();
                circuitBreaker.fail();
            }
        } else {
            System.out.println("Impossivel realizar a requisição");
        }

        return resultado;
    }
}
