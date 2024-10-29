package br.com.gubee.application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PingService implements PingUseCase {
    @Override
    public String ping() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://localhost:8080/ping")
        ).GET().build();

        String response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        System.out.println(response);

        return response;
    }
}
