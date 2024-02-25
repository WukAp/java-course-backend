package edu.java;

import edu.java.client.GithubClient;
import edu.java.client.StackoverflowClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.ClientConfig;
import edu.java.models.RelativeLinkModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ApplicationConfig.class, ClientConfig.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ScrapperApplication.class, args);
        GithubClient client = context.getBean(GithubClient.class);
        System.out.println(client.getLastModifier(new RelativeLinkModel("WukAp/telegram-bot")).block());;
        StackoverflowClient client2 = context.getBean(StackoverflowClient.class);
        System.out.println(client2.getLastModifier(new RelativeLinkModel(""+1077347)).block());;
    }

}
