package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.ObjectBuildingUtils.sendMessageBuilder;

@Component
public class HelpCommand implements Command {
    private final List<? extends Command> commands;

    @Autowired
    public HelpCommand(List<? extends Command> commands1) {
        this.commands = commands1;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с доступными в даннный момент командами";
    }

    @Override
    public SendMessage handle(Update update, Optional<UserStatus> status, TrackingDao trackingDao) {

        StringBuilder message = new StringBuilder("Доступные команды в данный момент:\n");
        commands.forEach(command -> {
            if (command.isAvailableToRun(status)) {
                message.append(command.commandAndDescriptionPrettyPrint());
            }
        });
        message.append(this.commandAndDescriptionPrettyPrint()); // не получается внедрить зависимость до её создания
        return sendMessageBuilder(update, message.toString());
    }

    @Override
    public boolean isAvailableToRun(Optional<UserStatus> status) {
        return true;
    }

}
