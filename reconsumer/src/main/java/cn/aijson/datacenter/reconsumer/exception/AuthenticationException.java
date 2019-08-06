package cn.aijson.datacenter.reconsumer.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
