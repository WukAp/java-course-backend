package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.*;

class StackoverflowClientTest {

    private static final StackoverflowClient client = new StackoverflowClient();
    private static WireMockServer wireMockServer;

    @BeforeEach
    void startWireMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        stubFor(get(urlEqualTo(("/54722250?site=stackoverflow"
        ))).willReturn(
            aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(
                    "{\"items\":[{\"tags\":[\"python\",\"python-3.x\"],\"owner\":{\"account_id\":35584,\"reputation\":21405,\"user_id\":100835,\"user_type\":\"registered\",\"accept_rate\":81,\"profile_image\":\"https://www.gravatar.com/avatar/12a7f3f439fd1b4eaa8bd2eea27cbb36?s=256&d=identicon&r=PG\",\"display_name\":\"MiffTheFox\",\"link\":\"https://stackoverflow.com/users/100835/miffthefox\"},\"is_answered\":true,\"view_count\":163439,\"closed_date\":1384530172,\"accepted_answer_id\":1077349,\"answer_count\":3,\"score\":136,\"last_activity_date\":170095869,\"creation_date\":1246580873,\"last_edit_date\":1399755590,\"question_id\":1077347,\"link\":\"https://stackoverflow.com/questions/1077347/hello-world-in-python\",\"closed_reason\":\"Duplicate\",\"title\":\"Hello World in Python\"}],\"has_more\":false,\"quota_max\":300,\"quota_remaining\":233}")
        ));
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
