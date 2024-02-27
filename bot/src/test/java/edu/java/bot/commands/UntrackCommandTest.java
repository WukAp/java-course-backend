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
import static edu.java.bot.commands.UpdateMockBuilder.getUpdateMock;
import static org.junit.jupiter.api.Assertions.*;

class UntrackCommandTest {

    private static final UntrackCommand untrackCommand = new UntrackCommand();

    @Test
    void command() {
        assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    void description() {
        assertEquals("прекратить отслеживание ссылки", untrackCommand.description());
    }

    @Test
    void handle() {
        Update update = UpdateMockBuilder.getUpdateMock(8L, 5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);

        SendMessage sendMessage =
            untrackCommand.handle(update, UserStatus.WAITING_FOR_UNTRACKING_LINK, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Отслеживание ссылки прекращено!",
            sendMessage.getParameters().get("text")
        );

        sendMessage = untrackCommand.handle(update, UserStatus.WAITING_FOR_COMMAND, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Введите ссылку, которую хотите прекратить отслеживать:",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(untrackCommand.isAvailableToRun( UserStatus.WAITING_FOR_COMMAND));
        assertFalse(untrackCommand.isAvailableToRun( UserStatus.WAITING_FOR_TRACKING_LINK));
        assertFalse(untrackCommand.isAvailableToRun( UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertFalse(untrackCommand.isAvailableToRun(UserStatus.UNREGISTRED));
    }
    @Test
    void support() {
        assertTrue(untrackCommand.supports(getUpdateMock("/untrack"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(untrackCommand.supports(getUpdateMock("/untrack test data"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(untrackCommand.supports(
            getUpdateMock("https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.WAITING_FOR_UNTRACKING_LINK
        ));
        assertFalse(untrackCommand.supports(getUpdateMock("/untrack"), UserStatus.UNREGISTRED));
        assertFalse(untrackCommand.supports(getUpdateMock("/untrack"), UserStatus.WAITING_FOR_TRACKING_LINK));

        assertFalse(untrackCommand.supports(getUpdateMock("/help"), UserStatus.UNREGISTRED));
        assertFalse(untrackCommand.supports(
            getUpdateMock(
                "https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.UNREGISTRED
        ));
        assertFalse(untrackCommand.supports(getUpdateMock("test data"), UserStatus.UNREGISTRED));
    }
}
