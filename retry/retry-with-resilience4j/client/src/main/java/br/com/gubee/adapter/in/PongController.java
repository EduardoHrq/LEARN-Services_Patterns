package br.com.gubee.adapter.in;

import br.com.gubee.application.usecases.PingUseCase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pong")
public class PongController {

    private final PingUseCase pingUseCase;

    public PongController(PingUseCase pingUseCase) {
        this.pingUseCase = pingUseCase;
    }

    @GetMapping
    @Async
    public String pong() {
        return pingUseCase.ping();
    }

}
