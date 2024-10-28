package br.com.gubee;

import br.com.gubee.circuitBreaker.Position;
import br.com.gubee.proxy.PingProxy;
import br.com.gubee.service.PingService;
import br.com.gubee.service.PingUseCase;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
//        PingUseCase pingUseCase = new PingService();
//
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();
//        pingUseCase.ping();

        PingUseCase ping = (PingUseCase) Proxy.newProxyInstance(
                PingService.class.getClassLoader(),
                PingService.class.getInterfaces(),
                new PingProxy(new PingService())
        );

        while (true) {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ping.ping();
        }
    }
}