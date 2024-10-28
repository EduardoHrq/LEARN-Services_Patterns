package br.com.gubee.service;

import org.springframework.stereotype.Service;

@Service
public class PingService implements PingUseCase {
    public String ping() {
        return "pong";
    }
}
