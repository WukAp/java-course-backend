package edu.java.bot.DAO;

import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserModel;
import edu.java.bot.models.UserStatus;
import java.util.List;
import java.util.Optional;

public interface TrackingDao {
    Optional<UserStatus> getStatus(long id);

    UserModel getUser(long id);

    void addUser(long id);

    void deleteUser(long id);

    void addLink(long id, LinkModel link);

    void removeLink(long id, LinkModel link);

    void getUserStatus(long id, UserStatus status);

    void setUserStatus(long id, UserStatus status);

    List<LinkModel> getLinks(long id);
}
