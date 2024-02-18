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

class TrackCommandTest {

    private static final TrackCommand trackCommand = new TrackCommand();

    @Test
    void command() {
        assertEquals("/track", trackCommand.command());
    }

    @Test
    void description() {
        assertEquals("начать отслеживание ссылки", trackCommand.description());
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

        SendMessage sendMessage =
            trackCommand.handle(update, Optional.of(UserStatus.WAITING_FOR_TRACKING_LINK), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Ссылка добавлена для отслеживания!",
            sendMessage.getParameters().get("text")
        );

        sendMessage = trackCommand.handle(update, Optional.of(UserStatus.WAITING_FOR_COMMAND), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Введите ссылку, которую хотите отслеживать:",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(trackCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_COMMAND)));
        assertFalse(trackCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_TRACKING_LINK)));
        assertFalse(trackCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_UNTRACKING_LINK)));
        assertFalse(trackCommand.isAvailableToRun(Optional.empty()));
    }
}
