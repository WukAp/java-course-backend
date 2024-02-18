package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.ObjectBuildingUtils.sendMessageBuilder;

@Component
public class ListCommand implements Command {
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update, Optional<UserStatus> status, TrackingDao trackingDao) {
        List<LinkModel> links = trackingDao.getLinks(update.message().from().id());
        if (links.isEmpty()) {
            return sendMessageBuilder(update, "Вы пока не добавили ссылки для отслеживания.");
        } else {
            return sendMessageBuilder(update, "Отслеживаемые ссылки:\n" + links);
        }
    }
}
