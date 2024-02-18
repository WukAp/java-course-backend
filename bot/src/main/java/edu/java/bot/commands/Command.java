package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;
import java.util.Optional;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update, Optional<UserStatus> status, TrackingDao trackingDao);

    default boolean isAvailableToRun(Optional<UserStatus> status) {
        return status.isPresent() && status.get().equals(UserStatus.WAITING_FOR_COMMAND);
    }

    default boolean supports(Update update, Optional<UserStatus> status) {
        return update.message().text().toLowerCase().trim().startsWith(command()) && isAvailableToRun(status);
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default String commandAndDescriptionPrettyPrint() {
        return command() + " - " + description() + '\n';
    }
}
