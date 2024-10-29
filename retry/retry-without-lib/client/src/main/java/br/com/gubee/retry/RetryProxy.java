package br.com.gubee.retry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RetryProxy implements InvocationHandler {

    private final Object target;

    public RetryProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final int maxAttempts = 3;
        final long waitTime = 2000L;

        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            try {
                return method.invoke(target, args);
            } catch (Exception e) {
                System.out.println("Tentativa [" + attempts + "] falhou");
                if (attempts < maxAttempts) {
                    Thread.sleep(waitTime);
                }
            }
        }

        System.out.println("Tentativas excedidas - requisição falhou");

        return "não foi possivel fazer a requisição";
    }
}
