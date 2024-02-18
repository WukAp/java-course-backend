package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {
    private static final HelpCommand emptyHelpCommand = new HelpCommand(List.of());

    @Test
    void command() {
        assertEquals("/help", emptyHelpCommand.command());
    }

    @Test
    void description() {
        assertEquals("вывести окно с доступными в даннный момент командами", emptyHelpCommand.description());
    }

    @Test
    void handleWithEmptyHelpCommand() {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(5L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);

        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);

        SendMessage sendMessage = emptyHelpCommand.handle(update, Optional.empty(), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals("Доступные команды в данный момент:\n" +
            "/help - вывести окно с доступными в даннный момент командами\n",
            sendMessage.getParameters().get("text"));
    }

    @Test
    void handleWithNotEmptyHelpCommand() {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(10L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);

        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);

        HelpCommand notEmptyHelpCommand =
            new HelpCommand(List.of(new StartCommand(), new TrackCommand()));

        SendMessage sendMessage = notEmptyHelpCommand.handle(update, Optional.empty(), trackingDao);

        assertEquals(10L, sendMessage.getParameters().get("chat_id"));
        assertEquals("Доступные команды в данный момент:\n" +
            "/start - зарегистрировать пользователя\n" +
            "/help - вывести окно с доступными в даннный момент командами\n",
            sendMessage.getParameters().get("text"));
    }

    @Test
    void isAvailableToRun() {
        assertTrue(emptyHelpCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_COMMAND)));
        assertTrue(emptyHelpCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_TRACKING_LINK)));
        assertTrue(emptyHelpCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_UNTRACKING_LINK)));
        assertTrue(emptyHelpCommand.isAvailableToRun(Optional.empty()));
    }
}
