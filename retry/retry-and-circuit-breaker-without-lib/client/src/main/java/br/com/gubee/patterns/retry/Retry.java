package br.com.gubee.patterns.retry;

public class Retry {

    public static  <T> T retry(RetryCallBack<T> retryCallBack, int maxAttempts, long waitTime) {
        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            try {
                return retryCallBack.doWithRetry();
            } catch (Exception e) {
                System.out.println("Tentativa [" + attempts + "] falhou. ");
                if (attempts < maxAttempts) {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        throw new RuntimeException("Max attempts reached");
    }
}
