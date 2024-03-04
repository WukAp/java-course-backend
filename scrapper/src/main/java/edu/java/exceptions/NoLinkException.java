package edu.java.exceptions;

public class NoLinkException extends IllegalArgumentException {
    public NoLinkException(int chatId, String link) {
        super("user with chat id = " + chatId + " does not contain url " + link);
    }
}
