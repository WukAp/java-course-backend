package edu.java.exceptions;

public class NoChatException extends IllegalArgumentException {
    public NoChatException(int id) {
        super("user with chat id = " + id + " does not exist");
    }
}
