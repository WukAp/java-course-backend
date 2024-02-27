package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.ObjectBuildingUtils.sendMessageBuilder;

@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update, UserStatus status, TrackingDao trackingDao) {
        trackingDao.addUser(update.message().from().id());
        return sendMessageBuilder(update, "Добро пожаловать в Updates bot!\n\n"
            + "Чтобы узнать список доступных команд, используйте /help");
    }

    @Override
    public boolean isAvailableToRun(UserStatus status) {
        return status.equals(UserStatus.UNREGISTRED);
    }
}
