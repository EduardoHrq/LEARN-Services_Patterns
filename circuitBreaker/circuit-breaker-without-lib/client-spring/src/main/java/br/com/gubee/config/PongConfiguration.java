package br.com.gubee.config;

import br.com.gubee.proxy.PongProxy;
import br.com.gubee.service.PongService;
import br.com.gubee.service.PongUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class PongConfiguration {

    @Bean
    public PongUseCase pongUseCase() {
        return (PongUseCase) Proxy.newProxyInstance(
                PongService.class.getClassLoader(),
                PongService.class.getInterfaces(),
                new PongProxy(new PongService())
        );
    }

}
