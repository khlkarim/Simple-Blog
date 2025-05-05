package dev.omarkarim.simple_blog.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg){
        super(msg);
    }

}