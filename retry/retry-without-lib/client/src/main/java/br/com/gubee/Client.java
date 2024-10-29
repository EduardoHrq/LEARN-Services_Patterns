package br.com.gubee;

import br.com.gubee.application.service.PingService;
import br.com.gubee.application.usecases.PingUseCase;
import br.com.gubee.retry.RetryImpl;
import br.com.gubee.retry.RetryProxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {

//        PingUseCase pingUseCase = new PingService();
//
//        pingUseCase.ping();

        /*Segunda maneira*/

//        PingUseCase pingUseCase = (PingUseCase) Proxy.newProxyInstance(
//                PingService.class.getClassLoader(),
//                PingService.class.getInterfaces(),
//                new RetryProxy(new PingService())
//        );
//
//        pingUseCase.ping();


        /*Terceira*/

        PingUseCase pingUseCase = new PingService();

        RetryImpl.retry(() -> {
            return pingUseCase.ping();
        }, 3, 2000L);


    }
}