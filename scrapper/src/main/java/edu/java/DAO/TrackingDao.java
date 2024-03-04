package edu.java.DAO;

import edu.java.exceptions.DoubleChatIdException;
import edu.java.exceptions.NoChatException;
import edu.java.models.LinkModel;
import java.net.URI;
import java.util.Set;

public interface TrackingDao {

    void addChat(int chatId) throws DoubleChatIdException;

    void deleteChat(int chatId) throws NoChatException;

    Set<LinkModel> getLinks(int chatId) throws NoChatException;

    void addLink(int chatId, URI link) throws NoChatException;

    void deleteLink(int chatId, String link);

}
