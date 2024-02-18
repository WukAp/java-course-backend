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

class ListCommandTest {

    private static final ListCommand listCommand = new ListCommand();

    @Test
    void command() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    void description() {
        assertEquals("показать список отслеживаемых ссылок", listCommand.description());
    }

    @Test
    void handleWithEmptyTrackingList() {
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
        Mockito.when(trackingDao.getLinks(5L)).thenReturn(List.of());

        SendMessage sendMessage = listCommand.handle(update, Optional.empty(), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Вы пока не добавили ссылки для отслеживания.",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void handleWithFullTrackingList() {
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
        List<LinkModel> list = List.of(new LinkModel("link1"), new LinkModel("link2"));
        Mockito.when(trackingDao.getLinks(8L)).thenReturn(list);

        SendMessage sendMessage = listCommand.handle(update, Optional.empty(), trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Отслеживаемые ссылки:\n" + list,
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(listCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_COMMAND)));
        assertFalse(listCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_TRACKING_LINK)));
        assertFalse(listCommand.isAvailableToRun(Optional.of(UserStatus.WAITING_FOR_UNTRACKING_LINK)));
        assertFalse(listCommand.isAvailableToRun(Optional.empty()));
    }
}
