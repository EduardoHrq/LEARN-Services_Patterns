package br.com.gubee;

import br.com.gubee.application.ports.in.PingUseCase;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    private PingUseCase pingUseCase;

    public PingController(PingUseCase pingUseCase) {
        this.pingUseCase = pingUseCase;
    }

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(pingUseCase.ping());
    }
}
