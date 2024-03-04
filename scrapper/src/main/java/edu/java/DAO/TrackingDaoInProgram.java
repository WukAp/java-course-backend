package edu.java.DAO;

import edu.java.exceptions.DoubleChatIdException;
import edu.java.exceptions.NoChatException;
import edu.java.models.LinkModel;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TrackingDaoInProgram implements TrackingDao {
    private final Map<Integer, ChatWithLinksHolder> data = new HashMap<>();

    @Override
    public void addChat(int chatId) {
        if (data.containsKey(chatId)) {
            throw new DoubleChatIdException(chatId);
        } else {
            data.put(chatId, new ChatWithLinksHolder(chatId));
        }
    }

    @Override
    public void deleteChat(int chatId) {
        boolean isNotContains = !data.remove(chatId, new ChatWithLinksHolder(chatId));
        if (isNotContains) {
            throw new NoChatException(chatId);
        }
    }

    @Override
    public Set<LinkModel> getLinks(int chatId) {
        if (data.containsKey(chatId)) {
            return data.get(chatId).linksList;
        } else {
            throw new NoChatException(chatId);
        }
    }

    @Override
    public void addLink(int chatId, URI link) {
        if (data.containsKey(chatId)) {
            data.get(chatId).addLink(generateLinkModel(link));
        } else {
            throw new NoChatException(chatId);
        }
    }

    @Override
    public void deleteLink(int chatId, String link) {
        if (data.containsKey(chatId)) {
            data.get(chatId).deleteLink(link);
        } else {
            throw new NoChatException(chatId);
        }
    }

    private static LinkModel generateLinkModel(URI link) {
        return new LinkModel(link, link + " description");
    }

    @RequiredArgsConstructor
    public static class ChatWithLinksHolder {
        private final Set<LinkModel> linksList = new HashSet<>();
        @Getter
        private final Integer chatId;

        public void addLink(LinkModel linkModel) {
            linksList.add(linkModel);
        }

        public void deleteLink(String link) {
            try {
                linksList.remove(new LinkModel(link, null));
            } catch (URISyntaxException e) {
                //do nothing
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ChatWithLinksHolder that = (ChatWithLinksHolder) o;
            return Objects.equals(chatId, that.chatId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(chatId);
        }

    }
}
