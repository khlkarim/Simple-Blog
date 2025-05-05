package dev.omarkarim.simple_blog.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String msg){
        super(msg);
    }

}