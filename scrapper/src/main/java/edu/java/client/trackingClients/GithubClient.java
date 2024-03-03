package edu.java.client.trackingClients;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.models.RelativeLinkModel;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GithubClient extends Client {
    private static final String DEFAULT_GITHUB_API_BASE_URL = "https://api.github.com/repos/";
    private static final String DEFAULT_GITHUB_BASE_URL = "https://github.com/";
    private final WebClient webClient;

    public GithubClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public GithubClient() {
        this(DEFAULT_GITHUB_API_BASE_URL);
    }

    @Override
    public RelativeLinkModel createLinkModel(String link) {
        //https://github.com/pengrad/java-telegram-bot-api
        Pattern p = Pattern.compile("^" + DEFAULT_GITHUB_BASE_URL + "([a-zA-Z0-9.,\\-_]+/[a-zA-Z0-9.,\\-_]+)(/?.*)$");
        Matcher m = p.matcher(link);
        if (m.find()) {
            return new RelativeLinkModel(m.group(1));
        } else {
            throw new IllegalArgumentException("Unsupported format for github repository url");
        }
    }

    @Override
    public Mono<OffsetDateTime> getLastModifier(RelativeLinkModel linkModel) {
        return webClient.get()
            .uri(linkModel.link())
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .map(questionResponse -> questionResponse.time);
    }

    public record QuestionResponse(@JsonProperty("updated_at") OffsetDateTime time) {
    }
}

