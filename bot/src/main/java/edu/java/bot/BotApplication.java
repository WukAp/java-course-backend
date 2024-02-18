package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.DAO.TrackingDao;
import edu.java.bot.commands.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.models.UserStatus;
import edu.java.bot.processors.UserMessageProcessor;
import java.util.Arrays;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static final Logger LOGGER = LogManager.getLogger(BotApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BotApplication.class, args);
        startBot(context);
    }

    private static TelegramBot startBot(ConfigurableApplicationContext context) {
        TelegramBot bot = new TelegramBot(getTelegramToken(context));

        UserMessageProcessor processor = context.getBean(UserMessageProcessor.class);
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                LOGGER.info(
                    "Сообщение принято от " + update.message().from().username() + ": " + update.message().text());
                SendMessage message = processor.process(update);
                bot.execute(getMyMenuCommands(context, update));
                var messageText = bot.execute(message).message().text();
                LOGGER.info("Отправлен ответ: " + messageText);
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
        return bot;
    }

    private static String getTelegramToken(ConfigurableApplicationContext context) {
        return context.getBean(ApplicationConfig.class).telegramToken();
    }

    private static SetMyCommands getMyMenuCommands(ConfigurableApplicationContext context, Update update) {
        Optional<UserStatus> status = context.getBean(TrackingDao.class).getStatus(update.message().from().id());
        BotCommand[] commands = context.getBeansOfType(Command.class).values().stream()
            .filter(command -> command.isAvailableToRun(status))
            .map(Command::toApiCommand).toArray(BotCommand[]::new);
        SetMyCommands cmd = new SetMyCommands(commands);
        cmd.scope(new BotCommandsScopeChat(update.message().chat().id()));
        return cmd;
    }
}
