package br.com.gubee.patterns.retry;

@FunctionalInterface
public interface RetryCallBack<T> {
    T doWithRetry() throws Exception;
}
