package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class StartCommandTest {

    private static final StartCommand startCommand = new StartCommand();

    @Test
    void command() {
        assertEquals("/start", startCommand.command());
    }

    @Test
    void description() {
        assertEquals("зарегистрировать пользователя", startCommand.description());
    }

    @Test
    void handle() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.id()).thenReturn(8L);

        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(5L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.from()).thenReturn(user);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);

        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);

        SendMessage sendMessage = startCommand.handle(update, Optional.empty(), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Добро пожаловать в Updates bot!\n\n"
                + "Чтобы узнать список доступных команд, используйте /help",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertFalse(startCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_COMMAND)));
        assertFalse(startCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_TRACKING_LINK)));
        assertFalse(startCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_UNTRACKING_LINK)));
        assertTrue(startCommand.isAvailableToRun(Optional.empty()));
    }
}
