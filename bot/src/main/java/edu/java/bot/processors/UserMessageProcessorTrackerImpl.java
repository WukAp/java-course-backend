package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.commands.Command;
import edu.java.bot.models.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.ObjectBuildingUtils.sendMessageBuilder;

@Component
public class UserMessageProcessorTrackerImpl implements UserMessageProcessor {

    private final List<? extends Command> commands;
    private final TrackingDao trackingDao;
    public static final String UNKNOWN_COMMAND_MESSAGE =
        "Неизвестная команда. \n\n"
            + "Чтобы узнать список доступных команд, используйте /help";

    @Autowired
    public UserMessageProcessorTrackerImpl(List<? extends Command> commands, TrackingDao trackingDao) {
        this.commands = commands;
        this.trackingDao = trackingDao;
    }

    @Override
    public List<? extends Command> commands() {
        return List.copyOf(commands);
    }

    @Override
    public SendMessage process(Update update) {
        Optional<UserStatus> status = trackingDao.getStatus(update.message().from().id());
        for (Command command : commands) {
            if (command.supports(update, status)) {
                return command.handle(update, status, trackingDao);
            }
        }
        return sendMessageBuilder(update, UNKNOWN_COMMAND_MESSAGE);
    }
}
