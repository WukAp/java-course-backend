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
//    private static WireMockServer wireMockServer;

//    @BeforeEach
//    void startWireMock() {
//        wireMockServer = new WireMockServer();
//        wireMockServer.start();
//        stubFor(get(urlEqualTo(("/54722250/site=stackoverflow"
//        ))).willReturn(
//            aResponse()
//                .withHeader("Content-Type", "application/json")
//                .withStatus(200)
//                .withBody(
//                    "{\n  \"id\": 756863853,\n  \"node_id\": \"R_kgDOLRzTbQ\",\n  \"name\": \"telegram-bot\",\n  \"full_name\": \"WukAp/telegram-bot\",\n  \"private\": false,\n  \"owner\": {\n    \"login\": \"WukAp\",\n    \"id\": 47060627,\n    \"node_id\": \"MDQ6VXNlcjQ3MDYwNjI3\",\n    \"avatar_url\": \"https://avatars.githubusercontent.com/u/47060627?v=4\",\n    \"gravatar_id\": \"\",\n    \"url\": \"https://api.github.com/users/WukAp\",\n    \"html_url\": \"https://github.com/WukAp\",\n    \"followers_url\": \"https://api.github.com/users/WukAp/followers\",\n    \"following_url\": \"https://api.github.com/users/WukAp/following{/other_user}\",\n    \"gists_url\": \"https://api.github.com/users/WukAp/gists{/gist_id}\",\n    \"starred_url\": \"https://api.github.com/users/WukAp/starred{/owner}{/repo}\",\n    \"subscriptions_url\": \"https://api.github.com/users/WukAp/subscriptions\",\n    \"organizations_url\": \"https://api.github.com/users/WukAp/orgs\",\n    \"repos_url\": \"https://api.github.com/users/WukAp/repos\",\n    \"events_url\": \"https://api.github.com/users/WukAp/events{/privacy}\",\n    \"received_events_url\": \"https://api.github.com/users/WukAp/received_events\",\n    \"type\": \"User\",\n    \"site_admin\": false\n  },\n  \"html_url\": \"https://github.com/WukAp/telegram-bot\",\n  \"description\": null,\n  \"fork\": false,\n  \"url\": \"https://api.github.com/repos/WukAp/telegram-bot\",\n  \"forks_url\": \"https://api.github.com/repos/WukAp/telegram-bot/forks\",\n  \"keys_url\": \"https://api.github.com/repos/WukAp/telegram-bot/keys{/key_id}\",\n  \"collaborators_url\": \"https://api.github.com/repos/WukAp/telegram-bot/collaborators{/collaborator}\",\n  \"teams_url\": \"https://api.github.com/repos/WukAp/telegram-bot/teams\",\n  \"hooks_url\": \"https://api.github.com/repos/WukAp/telegram-bot/hooks\",\n  \"issue_events_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues/events{/number}\",\n  \"events_url\": \"https://api.github.com/repos/WukAp/telegram-bot/events\",\n  \"assignees_url\": \"https://api.github.com/repos/WukAp/telegram-bot/assignees{/user}\",\n  \"branches_url\": \"https://api.github.com/repos/WukAp/telegram-bot/branches{/branch}\",\n  \"tags_url\": \"https://api.github.com/repos/WukAp/telegram-bot/tags\",\n  \"blobs_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/blobs{/sha}\",\n  \"git_tags_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/tags{/sha}\",\n  \"git_refs_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/refs{/sha}\",\n  \"trees_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/trees{/sha}\",\n  \"statuses_url\": \"https://api.github.com/repos/WukAp/telegram-bot/statuses/{sha}\",\n  \"languages_url\": \"https://api.github.com/repos/WukAp/telegram-bot/languages\",\n  \"stargazers_url\": \"https://api.github.com/repos/WukAp/telegram-bot/stargazers\",\n  \"contributors_url\": \"https://api.github.com/repos/WukAp/telegram-bot/contributors\",\n  \"subscribers_url\": \"https://api.github.com/repos/WukAp/telegram-bot/subscribers\",\n  \"subscription_url\": \"https://api.github.com/repos/WukAp/telegram-bot/subscription\",\n  \"commits_url\": \"https://api.github.com/repos/WukAp/telegram-bot/commits{/sha}\",\n  \"git_commits_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/commits{/sha}\",\n  \"comments_url\": \"https://api.github.com/repos/WukAp/telegram-bot/comments{/number}\",\n  \"issue_comment_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues/comments{/number}\",\n  \"contents_url\": \"https://api.github.com/repos/WukAp/telegram-bot/contents/{+path}\",\n  \"compare_url\": \"https://api.github.com/repos/WukAp/telegram-bot/compare/{base}...{head}\",\n  \"merges_url\": \"https://api.github.com/repos/WukAp/telegram-bot/merges\",\n  \"archive_url\": \"https://api.github.com/repos/WukAp/telegram-bot/{archive_format}{/ref}\",\n  \"downloads_url\": \"https://api.github.com/repos/WukAp/telegram-bot/downloads\",\n  \"issues_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues{/number}\",\n  \"pulls_url\": \"https://api.github.com/repos/WukAp/telegram-bot/pulls{/number}\",\n  \"milestones_url\": \"https://api.github.com/repos/WukAp/telegram-bot/milestones{/number}\",\n  \"notifications_url\": \"https://api.github.com/repos/WukAp/telegram-bot/notifications{?since,all,participating}\",\n  \"labels_url\": \"https://api.github.com/repos/WukAp/telegram-bot/labels{/name}\",\n  \"releases_url\": \"https://api.github.com/repos/WukAp/telegram-bot/releases{/id}\",\n  \"deployments_url\": \"https://api.github.com/repos/WukAp/telegram-bot/deployments\",\n  \"created_at\": \"2024-02-13T13:09:38Z\",\n  \"updated_at\": \"2024-02-19T14:39:36Z\",\n  \"pushed_at\": \"2024-02-23T23:17:48Z\",\n  \"git_url\": \"git://github.com/WukAp/telegram-bot.git\",\n  \"ssh_url\": \"git@github.com:WukAp/telegram-bot.git\",\n  \"clone_url\": \"https://github.com/WukAp/telegram-bot.git\",\n  \"svn_url\": \"https://github.com/WukAp/telegram-bot\",\n  \"homepage\": null,\n  \"size\": 95,\n  \"stargazers_count\": 0,\n  \"watchers_count\": 0,\n  \"language\": \"Java\",\n  \"has_issues\": true,\n  \"has_projects\": true,\n  \"has_downloads\": true,\n  \"has_wiki\": false,\n  \"has_pages\": false,\n  \"has_discussions\": false,\n  \"forks_count\": 0,\n  \"mirror_url\": null,\n  \"archived\": false,\n  \"disabled\": false,\n  \"open_issues_count\": 1,\n  \"license\": null,\n  \"allow_forking\": true,\n  \"is_template\": false,\n  \"web_commit_signoff_required\": false,\n  \"topics\": [\n\n  ],\n  \"visibility\": \"public\",\n  \"forks\": 0,\n  \"open_issues\": 1,\n  \"watchers\": 0,\n  \"default_branch\": \"master\",\n  \"temp_clone_token\": null,\n  \"network_count\": 0,\n  \"subscribers_count\": 1\n}")
//        ));
//    }
//
//    @Test
//    void getLastModifier() {
//
//        Client client1 = new StackoverflowClient(wireMockServer.baseUrl());
//        System.out.println(wireMockServer.baseUrl());
//        RelativeLinkModel model = client1.createLinkModel(
//            "https://stackoverflow.com/questions/54722250/wiremock-error-there-are-no-stub-mappings-in-this-wiremock-instance");
//        OffsetDateTime time = client1.getLastModifier(model).block();
//
//        long timestamp = 167729564L;
//        Instant instant = Instant.ofEpochSecond(timestamp);
//
//        assertEquals(instant.atOffset(ZoneOffset.UTC), time);
//    }

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

//    @AfterEach
//    void stopWireMock() {
//        wireMockServer.stop();
//    }
}
