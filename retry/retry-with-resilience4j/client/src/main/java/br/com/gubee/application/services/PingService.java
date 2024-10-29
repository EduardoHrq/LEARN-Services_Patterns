package br.com.gubee.application.services;

import br.com.gubee.application.usecases.PingUseCase;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PingService implements PingUseCase {

    private final RestTemplate restTemplate;

    public PingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Retry(name = "ping", fallbackMethod = "fallback")
    public String ping() {
        String resultado = null;

        try {
            resultado = restTemplate.getForObject("http://localhost:8080/ping", String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        return resultado;
    }

    public String fallback(Exception e) {
        return "erro";
    }
}
