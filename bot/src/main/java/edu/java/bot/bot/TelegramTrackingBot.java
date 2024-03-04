package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.commands.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.models.UserStatus;
import edu.java.bot.processors.UserMessageProcessor;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class TelegramTrackingBot implements Bot {

    private static final Logger LOGGER = LogManager.getLogger(TelegramTrackingBot.class);
    private TelegramBot bot;
    private final UserMessageProcessor processor;
    private final TrackingDao trackingDao;
    private final List<? extends Command> commands;
    private final ApplicationConfig config;

    @Autowired
    public TelegramTrackingBot(
        UserMessageProcessor processor,
        TrackingDao trackingDao,
        List<? extends Command> commands, ApplicationConfig config
    ) {
        this.processor = processor;
        this.trackingDao = trackingDao;
        this.commands = commands;
        this.config = config;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        //TODO
    }

    @Override
    public int process(List<Update> updates) {
        //TODO
        return 0;
    }

    @Override
    public void start() {
        bot = new TelegramBot(config.telegramToken());
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                LOGGER.info(
                    "Сообщение принято от {}.: {}.", update.message().from().username(), update.message().text());
                SendMessage message = processor.process(update);
                bot.execute(getMyMenuCommands(update));
                var messageText = bot.execute(message).message().text();
                LOGGER.info("Отправлен ответ: {}.", messageText);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                LOGGER.error(Arrays.toString(e.getStackTrace()));
            }
        });
    }

    @Override
    public void close() {
        //TODO
    }

    private SetMyCommands getMyMenuCommands(Update update) {
        UserStatus status = trackingDao.getUserStatus(update.message().from().id());
        BotCommand[] availableCommands = commands.stream()
            .filter(command -> command.isAvailableToRun(status))
            .map(Command::toApiCommand).toArray(BotCommand[]::new);
        SetMyCommands cmd = new SetMyCommands(availableCommands);
        cmd.scope(new BotCommandsScopeChat(update.message().chat().id()));
        return cmd;
    }
}
