package edu.java.bot.DAO;

import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserModel;
import edu.java.bot.models.UserStatus;
import java.util.List;

public interface TrackingDao {
    UserStatus getStatus(long id);

    UserModel getUser(long id);

    void addUser(long id);

    void deleteUser(long id);

    void addLink(long id, LinkModel link);

    void removeLink(long id, LinkModel link);

    UserStatus getUserStatus(long id);

    void setUserStatus(long id, UserStatus status);

    List<LinkModel> getLinks(long id);
}
