package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.mockito.Mockito;

public class UpdateMockBuilder {

    public static Update getUpdateMock(long chatId) {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }

    public static Update getUpdateMock(String text) {
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn(text);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }

    public static Update getUpdateMock(long userId, long chatId) {
        User user = Mockito.mock(User.class);
        Mockito.when(user.id()).thenReturn(userId);

        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.from()).thenReturn(user);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }

    public static Update getUpdateMock(String text, long userId, long chatId) {

        User user = Mockito.mock(User.class);
        Mockito.when(user.id()).thenReturn(userId);

        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.from()).thenReturn(user);
        Mockito.when(message.text()).thenReturn(text);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }
}
