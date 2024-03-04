package edu.java.bot.clients;

import edu.java.DTO.LinkResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {
    private static final String DEFAULT_URL = "http://localhost:8080/";
    private static final String LINK_URL = "/links/";
    private static final String TG_CHAT_URL = "/tg-chat/";

    private final WebClient webClient;

    public ScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public ScrapperClient() {
        this(DEFAULT_URL);
    }

    public Mono<String> addChat(int chatId) {
        return webClient.post()
            .uri(TG_CHAT_URL + chatId)
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> deleteChat(int chatId) {
        return webClient.delete()
            .uri(TG_CHAT_URL + chatId + "/")
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> addLink(LinkResponse linkRequest) {
        return webClient.post()
            .uri(LINK_URL)
            .body(Mono.just(linkRequest), LinkResponse.class)
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> deleteLink(LinkResponse linkRequest) {
        return webClient.delete()
            .uri(LINK_URL + linkRequest.tgChatId() + "/?url=" + linkRequest.url())
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getLink(int chatId) {
        return webClient.get()
            .uri(LINK_URL + chatId + "/")
            .retrieve()
            .bodyToMono(String.class);
    }
}
