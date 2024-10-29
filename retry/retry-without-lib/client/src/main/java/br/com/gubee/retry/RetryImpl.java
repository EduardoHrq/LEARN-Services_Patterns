package br.com.gubee.retry;

import java.util.concurrent.TimeUnit;

public class RetryImpl {

    public static <T> T retry(Retry<T> retry, int maxAttempts, long waitTime) {

        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            try {
                return retry.doWithRetry();
            } catch (Exception e) {
                System.out.println("Tentativa [" + attempts + "] falhou");
                if (attempts < maxAttempts) {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);

                    }
                }
            }
        }

        throw new RuntimeException("Tentativas excedidas - requisição falhou");
    }
}
