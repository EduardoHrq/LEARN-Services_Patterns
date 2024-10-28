package br.com.gubee.proxy;

import br.com.gubee.circuitBreaker.PingCircuitBreaker;
import br.com.gubee.circuitBreaker.Position;
import br.com.gubee.service.PingUseCase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;

public class PingProxy implements InvocationHandler {

    private final PingUseCase target;

    public PingProxy(PingUseCase pingUseCase) {
        this.target = pingUseCase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        System.out.println("init");

        if (PingCircuitBreaker.isClosed()) {
            try {
                method.invoke(target, args);
                PingCircuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("erro");
                PingCircuitBreaker.requestFailed();
            }
        } else if (PingCircuitBreaker.isHalfOpen()){
            try {
                method.invoke(target, args);
                PingCircuitBreaker.successRequest();
                PingCircuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("erro");
                PingCircuitBreaker.cleanSuccess();
                PingCircuitBreaker.requestFailed();
            }
        } else {
            System.err.println("Impossivel realizar a requisição");
        }

        return null;
    }
}
