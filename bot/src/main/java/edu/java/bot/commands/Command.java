package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.models.UserStatus;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update, UserStatus status, TrackingDao trackingDao);

    default boolean isAvailableToRun(UserStatus status) {
        return status.equals(UserStatus.WAITING_FOR_COMMAND);
    }

    default boolean supports(Update update, UserStatus status) {
        return isAvailableToRun(status)
            && update.message() != null
            && update.message().text() != null
            && update.message().text().toLowerCase().trim().startsWith(command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default String commandAndDescriptionPrettyPrint() {
        return command() + " - " + description() + '\n';
    }
}
