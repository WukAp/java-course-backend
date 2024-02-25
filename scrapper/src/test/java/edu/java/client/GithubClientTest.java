package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.models.RelativeLinkModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class GithubClientTest {
    private static final GithubClient client = new GithubClient();
    private static WireMockServer wireMockServer;

    @BeforeEach
    void startWireMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        stubFor(get(urlEqualTo(("/WukAp/telegram-bot"
        ))).willReturn(
            aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(
                    "{ \"id\": 756863853, \"node_id\": \"R_kgDOLRzTbQ\", \"name\": \"telegram-bot\", \"full_name\": \"WukAp/telegram-bot\", \"private\": false, \"owner\": { \"login\": \"WukAp\", \"id\": 47060627, \"node_id\": \"MDQ6VXNlcjQ3MDYwNjI3\", \"avatar_url\": \"https://avatars.githubusercontent.com/u/47060627?v=4\", \"gravatar_id\": \"\", \"url\": \"https://api.github.com/users/WukAp\", \"html_url\": \"https://github.com/WukAp\", \"followers_url\": \"https://api.github.com/users/WukAp/followers\", \"following_url\": \"https://api.github.com/users/WukAp/following{/other_user}\", \"gists_url\": \"https://api.github.com/users/WukAp/gists{/gist_id}\", \"starred_url\": \"https://api.github.com/users/WukAp/starred{/owner}{/repo}\", \"subscriptions_url\": \"https://api.github.com/users/WukAp/subscriptions\", \"organizations_url\": \"https://api.github.com/users/WukAp/orgs\", \"repos_url\": \"https://api.github.com/users/WukAp/repos\", \"events_url\": \"https://api.github.com/users/WukAp/events{/privacy}\", \"received_events_url\": \"https://api.github.com/users/WukAp/received_events\", \"type\": \"User\", \"site_admin\": false }, \"html_url\": \"https://github.com/WukAp/telegram-bot\", \"description\": null, \"fork\": false, \"url\": \"https://api.github.com/repos/WukAp/telegram-bot\", \"forks_url\": \"https://api.github.com/repos/WukAp/telegram-bot/forks\", \"keys_url\": \"https://api.github.com/repos/WukAp/telegram-bot/keys{/key_id}\", \"collaborators_url\": \"https://api.github.com/repos/WukAp/telegram-bot/collaborators{/collaborator}\", \"teams_url\": \"https://api.github.com/repos/WukAp/telegram-bot/teams\", \"hooks_url\": \"https://api.github.com/repos/WukAp/telegram-bot/hooks\", \"issue_events_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues/events{/number}\", \"events_url\": \"https://api.github.com/repos/WukAp/telegram-bot/events\", \"assignees_url\": \"https://api.github.com/repos/WukAp/telegram-bot/assignees{/user}\", \"branches_url\": \"https://api.github.com/repos/WukAp/telegram-bot/branches{/branch}\", \"tags_url\": \"https://api.github.com/repos/WukAp/telegram-bot/tags\", \"blobs_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/blobs{/sha}\", \"git_tags_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/tags{/sha}\", \"git_refs_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/refs{/sha}\", \"trees_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/trees{/sha}\", \"statuses_url\": \"https://api.github.com/repos/WukAp/telegram-bot/statuses/{sha}\", \"languages_url\": \"https://api.github.com/repos/WukAp/telegram-bot/languages\", \"stargazers_url\": \"https://api.github.com/repos/WukAp/telegram-bot/stargazers\", \"contributors_url\": \"https://api.github.com/repos/WukAp/telegram-bot/contributors\", \"subscribers_url\": \"https://api.github.com/repos/WukAp/telegram-bot/subscribers\", \"subscription_url\": \"https://api.github.com/repos/WukAp/telegram-bot/subscription\", \"commits_url\": \"https://api.github.com/repos/WukAp/telegram-bot/commits{/sha}\", \"git_commits_url\": \"https://api.github.com/repos/WukAp/telegram-bot/git/commits{/sha}\", \"comments_url\": \"https://api.github.com/repos/WukAp/telegram-bot/comments{/number}\", \"issue_comment_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues/comments{/number}\", \"contents_url\": \"https://api.github.com/repos/WukAp/telegram-bot/contents/{+path}\", \"compare_url\": \"https://api.github.com/repos/WukAp/telegram-bot/compare/{base}...{head}\", \"merges_url\": \"https://api.github.com/repos/WukAp/telegram-bot/merges\", \"archive_url\": \"https://api.github.com/repos/WukAp/telegram-bot/{archive_format}{/ref}\", \"downloads_url\": \"https://api.github.com/repos/WukAp/telegram-bot/downloads\", \"issues_url\": \"https://api.github.com/repos/WukAp/telegram-bot/issues{/number}\", \"pulls_url\": \"https://api.github.com/repos/WukAp/telegram-bot/pulls{/number}\", \"milestones_url\": \"https://api.github.com/repos/WukAp/telegram-bot/milestones{/number}\", \"notifications_url\": \"https://api.github.com/repos/WukAp/telegram-bot/notifications{?since,all,participating}\", \"labels_url\": \"https://api.github.com/repos/WukAp/telegram-bot/labels{/name}\", \"releases_url\": \"https://api.github.com/repos/WukAp/telegram-bot/releases{/id}\", \"deployments_url\": \"https://api.github.com/repos/WukAp/telegram-bot/deployments\", \"created_at\": \"2024-02-13T13:09:38Z\", \"updated_at\": \"2024-02-19T14:39:36Z\", \"pushed_at\": \"2024-02-25T13:06:36Z\", \"git_url\": \"git://github.com/WukAp/telegram-bot.git\", \"ssh_url\": \"git@github.com:WukAp/telegram-bot.git\", \"clone_url\": \"https://github.com/WukAp/telegram-bot.git\", \"svn_url\": \"https://github.com/WukAp/telegram-bot\", \"homepage\": null, \"size\": 108, \"stargazers_count\": 0, \"watchers_count\": 0, \"language\": \"Java\", \"has_issues\": true, \"has_projects\": true, \"has_downloads\": true, \"has_wiki\": false, \"has_pages\": false, \"has_discussions\": false, \"forks_count\": 0, \"mirror_url\": null, \"archived\": false, \"disabled\": false, \"open_issues_count\": 2, \"license\": null, \"allow_forking\": true, \"is_template\": false, \"web_commit_signoff_required\": false, \"topics\": [ ], \"visibility\": \"public\", \"forks\": 0, \"open_issues\": 2, \"watchers\": 0, \"default_branch\": \"master\", \"temp_clone_token\": null, \"network_count\": 0, \"subscribers_count\": 1 }")
        ));
    }

    @Test
    void getLastModifier() {
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
}
