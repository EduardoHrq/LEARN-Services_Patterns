package br.com.gubee.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PongService implements PongUseCase {
    @Override
    public String ping() {

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:8080/ping")
        ).GET().build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());

        return response.body();

    }
}
