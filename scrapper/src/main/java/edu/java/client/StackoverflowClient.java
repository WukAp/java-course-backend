package edu.java.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.models.RelativeLinkModel;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackoverflowClient extends Client {
    private static final String DEFAULT_STACKOVERFLOW_API_BASE_URL = "https://api.stackexchange.com/2.3/questions/";
    private static final String DEFAULT_STACKOVERFLOW_BASE_URL = "https://stackoverflow.com/questions/";
    private final WebClient webClient;

    public StackoverflowClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public StackoverflowClient() {
        this(DEFAULT_STACKOVERFLOW_API_BASE_URL);
    }

    @Override
    public RelativeLinkModel createLinkModel(String link) {
        //https://stackoverflow.com/questions/36253040/example-of-mockitos-argumentcaptor
        Pattern p = Pattern.compile("^" + DEFAULT_STACKOVERFLOW_BASE_URL + "([0-9]+)(/?.*)$");
        Matcher m = p.matcher(link);
        if (m.find()) {
            return new RelativeLinkModel(m.group(1));
        } else {
            throw new IllegalArgumentException("Unsupported format for stackoverflow repository link");
        }
    }
    @Override
    public Mono<OffsetDateTime> getLastModifier(RelativeLinkModel linkModel) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(linkModel.link())
                .queryParam("site", "stackoverflow")
                .build())
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .map(questionResponse ->
                questionResponse.items.getFirst().time());
    }


    public record QuestionResponse(List<ItemResponse> items) {
        record ItemResponse(@JsonProperty("last_activity_date") OffsetDateTime time) {
        }
    }
}
