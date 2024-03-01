package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static edu.java.bot.commands.UpdateMockBuilder.getUpdateMock;
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

        Update update = UpdateMockBuilder.getUpdateMock(8L, 5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        SendMessage sendMessage = startCommand.handle(update, UserStatus.WAITING_FOR_COMMAND, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Добро пожаловать в Updates bot!\n\n"
                + "Чтобы узнать список доступных команд, используйте /help",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertFalse(startCommand.isAvailableToRun( UserStatus.WAITING_FOR_COMMAND));
        assertFalse(startCommand.isAvailableToRun( UserStatus.WAITING_FOR_TRACKING_LINK));
        assertFalse(startCommand.isAvailableToRun( UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertTrue(startCommand.isAvailableToRun(UserStatus.UNREGISTRED));
    }
    @Test
    void support() {
        assertTrue(startCommand.supports(getUpdateMock("/start"), UserStatus.UNREGISTRED));
        assertTrue(startCommand.supports(getUpdateMock("/start test data"), UserStatus.UNREGISTRED));
        assertFalse(startCommand.supports(getUpdateMock("/start"), UserStatus.WAITING_FOR_COMMAND));
        assertFalse(startCommand.supports(getUpdateMock("/start"), UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertFalse(startCommand.supports(getUpdateMock("/start"), UserStatus.WAITING_FOR_TRACKING_LINK));

        assertFalse(startCommand.supports(getUpdateMock("/help"), UserStatus.UNREGISTRED));
        assertFalse(startCommand.supports(getUpdateMock(
                "https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.UNREGISTRED));
        assertFalse(startCommand.supports(getUpdateMock("test data"), UserStatus.UNREGISTRED));
    }
}
