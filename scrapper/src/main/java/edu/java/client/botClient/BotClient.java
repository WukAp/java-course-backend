package edu.java.client.botClient;

import edu.java.DTO.LinkUpdateRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private static final String DEFAULT_URL = "http://localhost:8090/";

    private final WebClient webClient;

    public BotClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public BotClient() {
        this(DEFAULT_URL);
    }

    public Mono<String> putUpdate(LinkUpdateRequest linkResponse) {
        return webClient.post()
            .uri("/updates/")
            .body(Mono.just(linkResponse), LinkUpdateRequest.class)
            .retrieve()
            .bodyToMono(String.class);
    }

}
