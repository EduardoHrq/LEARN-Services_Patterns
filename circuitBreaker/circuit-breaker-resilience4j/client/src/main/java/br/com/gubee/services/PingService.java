package br.com.gubee.services;

import br.com.gubee.usecases.PingUseCase;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PingService implements PingUseCase {

    private final RestTemplate restTemplate;

    public PingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker(name = "ping", fallbackMethod = "fallback")
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
        return e.getMessage();
    }

}
