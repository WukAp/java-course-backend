package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.models.UserStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class UserMessageProcessorTrackerImplTest {
    private static UserMessageProcessor processor;
    private static List<Command> commandList =
        List.of(new StartCommand(), new ListCommand(), new TrackCommand(), new UntrackCommand());

    @BeforeAll
    static void setUp() {
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        processor = new UserMessageProcessorTrackerImpl(commandList, trackingDao);
    }

    @Test
    void commands() {
        assertArrayEquals(commandList.toArray(), processor.commands().toArray());
    }

    @Test
    void process() {
        Update update = getUpdateMock("/start");
        SendMessage sendMessage = processor.process(update);
        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Добро пожаловать в Updates bot!\n\n"
                + "Чтобы узнать список доступных команд, используйте /help",
            sendMessage.getParameters().get("text")
        );
    }

    private Update getUpdateMock(String text) {

        User user = Mockito.mock(User.class);
        Mockito.when(user.id()).thenReturn(8L);

        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(5L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.from()).thenReturn(user);
        Mockito.when(message.text()).thenReturn(text);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }
}
