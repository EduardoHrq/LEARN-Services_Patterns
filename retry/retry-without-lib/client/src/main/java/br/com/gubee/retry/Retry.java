package br.com.gubee.retry;

@FunctionalInterface
public interface Retry<T> {
    T doWithRetry() throws Exception;
}
