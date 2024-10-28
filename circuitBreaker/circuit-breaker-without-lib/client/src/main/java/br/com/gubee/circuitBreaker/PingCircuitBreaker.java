package br.com.gubee.circuitBreaker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingCircuitBreaker {
    //    private static PingCircuitBreaker instance = null;
    public static final Long INVOCATION_TIMEOUT = 5L;
    static final int LIMITE_FALHA = 5;
    static int falhas = 0;
    static final int LIMITE_SUCESSO = 3;
    static int sucesso = 0;
    private static Position estado = Position.CLOSED;


    private PingCircuitBreaker() {
    }
    public static void requestFailed() {
        falhas++;
        if (limitFails()) {
            System.out.println("Mudando Circuit Breaker para OPEN");
            switchPosition(Position.OPEN);
            cleanFails();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                try {
                    switchPosition(Position.HALF_OPEN);
                    System.out.println("Mudando Circuit Breaker para HALF_OPEN");
                } finally {
                    executor.shutdownNow();
                }
            }, INVOCATION_TIMEOUT, TimeUnit.SECONDS);

        }
    }

    public static void successRequest() {
        sucesso++;
        if (limitSuccess()) {
            System.out.println("3 sucesso, mudando para CLOSED");
            switchPosition(Position.CLOSED);
            System.out.println("Mudando Circuit Breaker para CLOSED");
        }
    }

    private static boolean limitFails() {
        return falhas >= LIMITE_FALHA;
    }

    private static boolean limitSuccess() {
        return sucesso >= LIMITE_SUCESSO;
    }

    public static boolean isClosed() {
        return estado == Position.CLOSED;
    }

    public static boolean isHalfOpen() {
        return estado == Position.HALF_OPEN;
    }

    public static void cleanFails() {
        falhas = 0;
    }

    public static void cleanSuccess() {
        sucesso = 0;
    }

    public static void switchPosition(Position position) {
        estado = position;
    }

}
