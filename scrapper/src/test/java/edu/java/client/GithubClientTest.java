package edu.java.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;
@WireMockTest
class GithubClientTest {
    private static final GithubClient client = new GithubClient();
    private static final WireMock githubWireMock = WireMock.create()
        .scheme("http")
        .host("api.github.com")
        .port(80)
        .build();

    @BeforeAll
    static void loadMappingForWireMock() {
        githubWireMock.loadMappingsFrom("src/test/resources/github-wiremock-stuff/");
    }

    @Test
    void getLastModifier() {
        RelativeLinkModel model = client.createLinkModel("https://github.com/WukAp/telegram-bot");
        OffsetDateTime time = client.getLastModifier(model).block();
        assertEquals(OffsetDateTime.parse("2024-02-19T14:39:36Z"), time);
    }

    @Test
    void createLinkModel() {
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api"));
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/"));
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/actions"));

        assertThrows(IllegalArgumentException.class, ()->client.createLinkModel("https://stackoverflow.com/54722250"));
        assertThrows(IllegalArgumentException.class, ()->client.createLinkModel("https://github.com/pengrad/"));
    }
}
