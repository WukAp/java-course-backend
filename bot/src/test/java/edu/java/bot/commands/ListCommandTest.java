package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserStatus;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static edu.java.bot.commands.UpdateMockBuilder.getUpdateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Update update = UpdateMockBuilder.getUpdateMock(8L, 5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        Mockito.when(trackingDao.getLinks(5L)).thenReturn(List.of());

        SendMessage sendMessage = listCommand.handle(update, UserStatus.WAITING_FOR_COMMAND, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Вы пока не добавили ссылки для отслеживания.",
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void handleWithFullTrackingList() throws URISyntaxException {
        Update update = UpdateMockBuilder.getUpdateMock(8L, 5L);
        TrackingDao trackingDao = Mockito.mock(TrackingDao.class);
        List<LinkModel> list = List.of(new LinkModel("link1"), new LinkModel("link2"));
        Mockito.when(trackingDao.getLinks(8L)).thenReturn(list);
        SendMessage sendMessage = listCommand.handle(update, UserStatus.WAITING_FOR_COMMAND, trackingDao);

        assertEquals(5L, sendMessage.getParameters().get("chat_id"));
        assertEquals(
            "Отслеживаемые ссылки:\n" + list,
            sendMessage.getParameters().get("text")
        );
    }

    @Test
    void isAvailableToRun() {
        assertTrue(listCommand.isAvailableToRun(UserStatus.WAITING_FOR_COMMAND));
        assertFalse(listCommand.isAvailableToRun(UserStatus.WAITING_FOR_TRACKING_LINK));
        assertFalse(listCommand.isAvailableToRun(UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertFalse(listCommand.isAvailableToRun(UserStatus.UNREGISTRED));
    }

    @Test
    void support() {
        assertTrue(listCommand.supports(getUpdateMock("/list"), UserStatus.WAITING_FOR_COMMAND));
        assertTrue(listCommand.supports(getUpdateMock("/list test data"), UserStatus.WAITING_FOR_COMMAND));
        assertFalse(listCommand.supports(getUpdateMock("/list"), UserStatus.UNREGISTRED));
        assertFalse(listCommand.supports(getUpdateMock("/list"), UserStatus.WAITING_FOR_UNTRACKING_LINK));
        assertFalse(listCommand.supports(getUpdateMock("/list"), UserStatus.WAITING_FOR_TRACKING_LINK));

        assertFalse(listCommand.supports(getUpdateMock("/start"), UserStatus.WAITING_FOR_COMMAND));
        assertFalse(listCommand.supports(
            getUpdateMock(
                "https://stackoverflow.com/questions/9772618/writing-junit-tests"),
            UserStatus.WAITING_FOR_COMMAND
        ));
        assertFalse(listCommand.supports(getUpdateMock("test data"), UserStatus.WAITING_FOR_COMMAND));
    }
}
