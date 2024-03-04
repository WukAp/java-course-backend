package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.BodyReadHelper;
import edu.java.client.trackingClients.Client;
import edu.java.client.trackingClients.GithubClient;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class GithubClientTest {
    private static final GithubClient client = new GithubClient();
    private static WireMockServer wireMockServer;
    private static BodyReadHelper readHelper;

    @BeforeAll
    static void getMockBody() throws FileNotFoundException {
        readHelper = new BodyReadHelper();
    }

    @BeforeEach
    void startWireMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        stubFor(get(urlEqualTo(("/WukAp/telegram-bot"
        ))).willReturn(
            aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(readHelper.getGithubBody())));
    }

    @Test
    void getLastModifier() {
        // System.out.println(wireMockServer.baseUrl());
        Client client1 = new GithubClient(wireMockServer.baseUrl());
        RelativeLinkModel model = client1.createLinkModel(
            "https://github.com/WukAp/telegram-bot");
        OffsetDateTime time = client1.getLastModifier(model).block();

        assertEquals(OffsetDateTime.parse("2024-02-19T14:39:36Z"), time);
    }

    @Test
    void createLinkModel() {
        assertEquals(
            new RelativeLinkModel("pengrad/java-telegram-bot-api"),
            client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api")
        );
        assertEquals(
            new RelativeLinkModel("pengrad/java-telegram-bot-api"),
            client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/")
        );
        assertEquals(
            new RelativeLinkModel("pengrad/java-telegram-bot-api"),
            client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/actions")
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> client.createLinkModel("https://stackoverflow.com/54722250")
        );
        assertThrows(IllegalArgumentException.class, () -> client.createLinkModel("https://github.com/pengrad/"));
    }

    @AfterEach
    void stopWireMock() {
        wireMockServer.stop();
    }
}
