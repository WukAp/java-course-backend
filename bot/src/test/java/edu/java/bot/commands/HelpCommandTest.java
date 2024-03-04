package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.commands.UpdateMockBuilder.getUpdateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Update update = getUpdateMock(5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        SendMessage sendMessage = emptyHelpCommand.handle(update, UserStatus.UNREGISTRED, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Доступные команды в данный момент:\n" +
                "/help - вывести окно с доступными в даннный момент командами\n",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void handleWithNotEmptyHelpCommand() {
        Update update = getUpdateMock(10L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        HelpCommand notEmptyHelpCommand =
            new HelpCommand(List.of(new StartCommand(), new TrackCommand()));
        SendMessage sendMessage = notEmptyHelpCommand.handle(update, UserStatus.UNREGISTRED, trackingDao);

        assertEquals(10L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Доступные команды в данный момент:\n" +
                "/start - зарегистрировать пользователя\n" +
                "/help - вывести окно с доступными в даннный момент командами\n",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(emptyHelpCommand.isAvailableToRun(UserStatus.WAITING_FOR_COMMAND));
        assertTrue(emptyHelpCommand.isAvailableToRun(UserStatus.WAITING_FOR_TRACKING_LINK));
        assertTrue(emptyHelpCommand.isAvailableToRun(UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertTrue(emptyHelpCommand.isAvailableToRun(UserStatus.UNREGISTRED));
    }

    @Test
    void support() {
        assertTrue(emptyHelpCommand.supports(getUpdateMock("/help"), UserStatus.UNREGISTRED));
        assertTrue(emptyHelpCommand.supports(getUpdateMock("/help"), UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertTrue(emptyHelpCommand.supports(getUpdateMock("/help"), UserStatus.WAITING_FOR_TRACKING_LINK));
        assertTrue(emptyHelpCommand.supports(getUpdateMock("/help"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(emptyHelpCommand.supports(getUpdateMock("/help test data"), UserStatus.WAITING_FOR_COMMAND));

        assertFalse(emptyHelpCommand.supports(getUpdateMock("/start"), UserStatus.UNREGISTRED));
        assertFalse(emptyHelpCommand.supports(
            getUpdateMock(
                "https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.WAITING_FOR_UNTRACKING_LINK
        ));
        assertFalse(emptyHelpCommand.supports(getUpdateMock("test data"), UserStatus.WAITING_FOR_TRACKING_LINK));
    }
}
