package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.BodyReadHelper;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.*;

class StackoverflowClientTest {

    private static final StackoverflowClient client = new StackoverflowClient();
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
        stubFor(get(urlEqualTo(("/54722250?site=stackoverflow"
        ))).willReturn(
            aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(readHelper.getStackoverflowBody())));
    }

    @Test
    void getLastModifier() {
        Client client1 = new StackoverflowClient(wireMockServer.baseUrl());
        RelativeLinkModel model = client1.createLinkModel(
            "https://stackoverflow.com/questions/54722250/wiremock-error-there-are-no-stub-mappings-in-this-wiremock-instance");
        OffsetDateTime time = client1.getLastModifier(model).block();

        long timestamp = 170095869;
        Instant instant = Instant.ofEpochSecond(timestamp);

        assertEquals(instant.atOffset(ZoneOffset.UTC), time);
    }

    @Test
    void createLinkModel() {
        assertEquals(
            new RelativeLinkModel("54722250"),
            client.createLinkModel("https://stackoverflow.com/questions/54722250")
        );
        assertEquals(
            new RelativeLinkModel("54722250"),
            client.createLinkModel("https://stackoverflow.com/questions/54722250/")
        );
        assertEquals(
            new RelativeLinkModel("54722250"),
            client.createLinkModel(
                "https://stackoverflow.com/questions/54722250/wiremock-error-there-are-no-stub-mappings-in-this-wiremock-instance")
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> client.createLinkModel("https://stackoverflow.com/54722250")
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api")
        );
    }

    @AfterEach
    void stopWireMock() {
        wireMockServer.stop();
    }
}
