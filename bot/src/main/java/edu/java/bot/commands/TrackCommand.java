package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserStatus;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.ObjectBuildingUtils.sendMessageBuilder;

@Component
public class TrackCommand implements Command {
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update, UserStatus status, TrackingDao trackingDao) {
        if (status.equals(UserStatus.WAITING_FOR_TRACKING_LINK)) {
            trackingDao.addLink(update.message().from().id(), new LinkModel(update.message().text()));
            trackingDao.setUserStatus(update.message().from().id(), UserStatus.WAITING_FOR_COMMAND);
            return sendMessageBuilder(update, "Ссылка добавлена для отслеживания!");

        } else {
            trackingDao.setUserStatus(update.message().from().id(), UserStatus.WAITING_FOR_TRACKING_LINK);
            return sendMessageBuilder(update, "Введите ссылку, которую хотите отслеживать:");
        }
    }

    @Override
    public boolean supports(Update update, UserStatus status) {
        return (status.equals(UserStatus.WAITING_FOR_COMMAND)
            && update.message().text().toLowerCase().trim().startsWith(command())
            || status.equals(UserStatus.WAITING_FOR_TRACKING_LINK));
    }
}
