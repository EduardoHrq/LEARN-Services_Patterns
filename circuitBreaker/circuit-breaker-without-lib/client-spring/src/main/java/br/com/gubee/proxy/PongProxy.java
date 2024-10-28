package br.com.gubee.proxy;

import br.com.gubee.circuitBreaker.PingCircuitBreaker;
import br.com.gubee.service.PongUseCase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PongProxy implements InvocationHandler {

    private final PongUseCase target;

    public PongProxy(PongUseCase pingUseCase) {
        this.target = pingUseCase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String resultado = null;

        if (PingCircuitBreaker.isClosed()) {
            try {
                resultado = (String) method.invoke(target, args);
                PingCircuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("erro");
                resultado = "erro";
                PingCircuitBreaker.requestFailed();
            }
        } else if (PingCircuitBreaker.isHalfOpen()){
            try {
                resultado = (String) method.invoke(target, args);
                PingCircuitBreaker.successRequest();
                PingCircuitBreaker.cleanFails();
            } catch (Exception e) {
                System.out.println("erro");
                resultado = "erro";
                PingCircuitBreaker.cleanSuccess();
                PingCircuitBreaker.requestFailed();
            }
        } else {
            System.err.println("Impossivel realizar a requisição");
            resultado = "Impossivel realizar a requisição";
        }

        return resultado;
    }
}
