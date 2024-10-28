package br.com.gubee.adapter.in;

import br.com.gubee.usecases.PingUseCase;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    private final PingUseCase pingUseCase;

    public PingController(PingUseCase pingUseCase) {
        this.pingUseCase = pingUseCase;
    }

    @GetMapping
    public ResponseEntity<String> ping() {

        try {
            return ResponseEntity.ok(pingUseCase.ping());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }

    }


}
