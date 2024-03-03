package edu.java.exceptions;

public class DoubleLinkException  extends IllegalArgumentException {
    public DoubleLinkException(int chatId, String link) {
        super("user with chat id = " + chatId + " already contains url " + link);
    }
}
