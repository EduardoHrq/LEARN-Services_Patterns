package br.com.gubee.adapter.in;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    @GetMapping
    @RateLimiter(name = "ping", fallbackMethod = "fallback")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    public ResponseEntity<String> fallback(Exception e) {
        return ResponseEntity.status(429).body("Too many requests");
    }
}
