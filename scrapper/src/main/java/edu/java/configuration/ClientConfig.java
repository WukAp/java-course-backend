package edu.java.configuration;

import edu.java.client.GithubClient;
import edu.java.client.StackoverflowClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
public record ClientConfig(String githubBaseUrl, String stackoverflowBaseUrl) {

    @Bean
    public GithubClient githubWebClient() {
        if (githubBaseUrl == null) {
            return new GithubClient();
        } else {
            return new GithubClient(githubBaseUrl);
        }
    }

    @Bean
    public StackoverflowClient stackoverflowWebClient() {
        if (stackoverflowBaseUrl == null) {
            return new StackoverflowClient();
        } else {
            return new StackoverflowClient(stackoverflowBaseUrl);
        }
    }
}
