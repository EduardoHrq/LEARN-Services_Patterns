package br.com.gubee.patterns.circuitbreaker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CircuitBreakerDefault implements CircuitBreaker {
    public final Long INVOCATION_TIMEOUT = 3L;
    private final int LIMITE_FALHA = 4;
    private int falhas = 0;
    private final int LIMITE_SUCESSO = 2;
    private int sucesso = 0;
    private Position estado = Position.CLOSED;

    @Override
    public void requestFailed() {
        this.falhas++;
        if (this.limitFails()) {
            System.out.println("Mudando Circuit Breaker para OPEN");
            this.switchPosition(Position.OPEN);
            cleanFails();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                try {
                    this.switchPosition(Position.HALF_OPEN);
                    System.out.println("Mudando Circuit Breaker para HALF_OPEN");
                } finally {
                    executor.shutdownNow();
                }
            }, INVOCATION_TIMEOUT, TimeUnit.SECONDS);

        }
    }

    @Override
    public void successRequest() {
        this.sucesso++;
        if (this.limitSuccess()) {
            switchPosition(Position.CLOSED);
            System.out.println("Mudando Circuit Breaker para CLOSED");
        }
    }

    private boolean limitFails() {
        if (this.isHalfOpen()) {
            return this.falhas >= 2;
        } else {
            return this.falhas >= LIMITE_FALHA;
        }
    }

    private boolean limitSuccess() {
        return this.sucesso >= LIMITE_SUCESSO;
    }

    @Override
    public boolean isClosed() {
        return this.estado == Position.CLOSED;
    }

    @Override
    public boolean isHalfOpen() {
        return this.estado == Position.HALF_OPEN;
    }

    @Override
    public void cleanFails() {
        this.falhas = 0;
    }

    @Override
    public void cleanSuccess() {
        this.sucesso = 0;
    }

    @Override
    public void fail() {
        this.falhas = 4;
        if (this.limitFails()) {
            System.out.println("Mudando Circuit Breaker para OPEN");
            this.switchPosition(Position.OPEN);
            cleanFails();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                try {
                    this.switchPosition(Position.HALF_OPEN);
                    System.out.println("Mudando Circuit Breaker para HALF_OPEN");
                } finally {
                    executor.shutdownNow();
                }
            }, INVOCATION_TIMEOUT, TimeUnit.SECONDS);

        }
    }

    private void switchPosition(Position position) {
        this.estado = position;
    }
}
