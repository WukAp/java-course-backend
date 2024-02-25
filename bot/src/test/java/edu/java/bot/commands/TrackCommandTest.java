package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static edu.java.bot.commands.UpdateMockBuilder.getUpdateMock;
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
        Update update = UpdateMockBuilder.getUpdateMock(8L, 5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        SendMessage sendMessage =
            trackCommand.handle(update, UserStatus.WAITING_FOR_TRACKING_LINK, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Ссылка добавлена для отслеживания!",
            sendMessage.getParameters().get("text")
        );

        sendMessage = trackCommand.handle(update, UserStatus.WAITING_FOR_COMMAND, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Введите ссылку, которую хотите отслеживать:",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(trackCommand.isAvailableToRun(UserStatus.WAITING_FOR_COMMAND));
        assertFalse(trackCommand.isAvailableToRun(UserStatus.WAITING_FOR_TRACKING_LINK));
        assertFalse(trackCommand.isAvailableToRun(UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertFalse(trackCommand.isAvailableToRun(UserStatus.UNREGISTRED));
    }

    @Test
    void support() {
        assertTrue(trackCommand.supports(getUpdateMock("/track"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(trackCommand.supports(getUpdateMock("/track test data"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(trackCommand.supports(
            getUpdateMock("https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.WAITING_FOR_TRACKING_LINK
        ));
        assertFalse(trackCommand.supports(getUpdateMock("/track"), UserStatus.UNREGISTRED));
        assertFalse(trackCommand.supports(getUpdateMock("/track"), UserStatus.WAITING_FOR_UNTRACKING_LINK));

        assertFalse(trackCommand.supports(getUpdateMock("/help"), UserStatus.UNREGISTRED));
        assertFalse(trackCommand.supports(
            getUpdateMock(
                "https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.UNREGISTRED
        ));
        assertFalse(trackCommand.supports(getUpdateMock("test data"), UserStatus.UNREGISTRED));
    }
}
