package br.com.gubee.adapter.in;

import br.com.gubee.service.PingService;
import br.com.gubee.service.PingUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    private final PingUseCase pingService;

    public PingController(PingUseCase pingService) {
        this.pingService = pingService;
    }

    @GetMapping
    public String ping() {
        return pingService.ping();
    }

}
