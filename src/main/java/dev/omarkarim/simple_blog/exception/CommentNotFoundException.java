package dev.omarkarim.simple_blog.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String msg){
        super(msg);
    }
    
}   

