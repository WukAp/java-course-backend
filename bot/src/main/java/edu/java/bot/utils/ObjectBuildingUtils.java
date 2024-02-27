package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class ObjectBuildingUtils {
    private ObjectBuildingUtils() {
    }

    public static SendMessage sendMessageBuilder(Update updateToAnswer, String text) {
        return new SendMessage(updateToAnswer.message().chat().id(), text);
    }
}
