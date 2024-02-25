package edu.java.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GithubClientTest {
    private static final GithubClient client = new GithubClient();

    @Test
    void createLinkModel() {
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api"));
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/"));
        assertEquals(new RelativeLinkModel("pengrad/java-telegram-bot-api"), client.createLinkModel("https://github.com/pengrad/java-telegram-bot-api/actions"));

        assertThrows(IllegalArgumentException.class, ()->client.createLinkModel("https://stackoverflow.com/54722250"));
        assertThrows(IllegalArgumentException.class, ()->client.createLinkModel("https://github.com/pengrad/"));
    }
}
