package br.com.gubee.application.services;

import br.com.gubee.application.ports.in.PingUseCase;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
public class PingService implements PingUseCase {

    private final RestTemplate restTemplate;

    public PingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String ping() {

        TimeLimiterConfig config = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build();
        TimeLimiter timeLimiter = TimeLimiter.of("ping", config);




        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:8080/ping")).GET().build();

        String response = null;

        try {
            response = timeLimiter.executeFutureSupplier(() -> CompletableFuture.supplyAsync(() -> {
                try {
                    return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (Exception e) {
            return "error, tempo excedido";
        }

        System.out.println(response);

        return response;
    }
}
