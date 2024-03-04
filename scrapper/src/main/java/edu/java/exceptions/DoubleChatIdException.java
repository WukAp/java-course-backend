package edu.java.exceptions;

public class DoubleChatIdException extends IllegalArgumentException {
    public DoubleChatIdException(int id) {
        super("user with chat id = " + id + " already exists");
    }
}
