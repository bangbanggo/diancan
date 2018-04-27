package edu.black.exception;

public class NotHaveOneId extends Exception {
    public NotHaveOneId() {
        super("没有获取到相应的ID");
    }

    public NotHaveOneId(String message) {
        super(message);
    }
}
