package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ObjectBuildingUtilsTest {

    @Test
    void sendMessageBuilder() {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(5L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);

        SendMessage sendMessage = ObjectBuildingUtils.sendMessageBuilder(update, "testText");

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals("testText", sendMessage.getParameters().get("text"));
    }
}
