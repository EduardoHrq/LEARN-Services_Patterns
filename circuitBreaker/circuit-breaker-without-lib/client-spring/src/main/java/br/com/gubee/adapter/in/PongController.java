package br.com.gubee.adapter.in;

import br.com.gubee.service.PongUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get/ping")
public class PongController {

    private final PongUseCase pingUseCase;

    public PongController(PongUseCase pingUseCase) {
        this.pingUseCase = pingUseCase;
    }

    @GetMapping
    public String ping() {
//        if (pingUseCase.ping().contains("erro") || pingUseCase.ping().contains("Impossivel")) {
//
//        }
        return pingUseCase.ping();
    }
}
