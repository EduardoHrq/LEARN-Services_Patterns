package br.com.gubee;

import br.com.gubee.application.PingService;
import br.com.gubee.application.PingUseCase;
import br.com.gubee.patterns.circuitbreaker.CircuitBreakerDefault;
import br.com.gubee.proxy.RetryAndCircuitBreakerProxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {

        PingUseCase pingUseCase = (PingUseCase) Proxy.newProxyInstance(
                PingService.class.getClassLoader(),
                PingService.class.getInterfaces(),
                new RetryAndCircuitBreakerProxy(new PingService(), new CircuitBreakerDefault())
        );

        while (true) {
            pingUseCase.ping();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}