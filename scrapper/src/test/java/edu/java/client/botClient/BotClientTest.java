package edu.java.client.botClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.DTO.LinkUpdateRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

class BotClientTest {
    private static WireMockServer wireMockServer;

    @BeforeEach
    void startWireMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        stubFor(post(urlEqualTo(("/updates/"
        ))).willReturn(
            aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("link has been updated!")));
    }

    @SneakyThrows
    @Test
    public void putUpdate() {
        BotClient client = new BotClient(wireMockServer.baseUrl());
       Assertions.assertEquals("link has been updated!",
           client.putUpdate(new LinkUpdateRequest(new URI("link"), "description!", 1)).block());
    }
}
