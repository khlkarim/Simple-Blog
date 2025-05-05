package dev.omarkarim.simple_blog.exception;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String msg){
        super(msg);
    }

}
