package edu.java.bot.DAO;

import edu.java.bot.models.LinkModel;
import edu.java.bot.models.UserModel;
import edu.java.bot.models.UserStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TrackingDAOInProgram implements TrackingDao {

    public static final Logger LOGGER = LogManager.getLogger(TrackingDAOInProgram.class);
    private final Set<UserModel> userDataSet = new HashSet<>();

    @Override
    public void addUser(long id) {
        userDataSet.add(new UserModel(id));
        LOGGER.info("был добавлен user с id = " + id);
    }

    @Override
    public Optional<UserStatus> getStatus(long id) {
        UserModel userModel = getUser(id);
        return (userModel == null) ? Optional.empty() : Optional.of(userModel.getStatus());
    }

    @Override
    public UserModel getUser(long id) {
        for (UserModel userModel : userDataSet) {
            if (userModel.getId() == id) {
                return userModel;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userDataSet.remove(new UserModel(id));
        LOGGER.info("был удалён user с id = " + id);
    }

    @Override
    public void addLink(long id, LinkModel link) {
        findUserByIdOrThrowException(id).addNewLink(link);
        LOGGER.info("была добавлена ссылка " + link + " для user'а с id = " + id);
    }

    @Override
    public void removeLink(long id, LinkModel link) {
        findUserByIdOrThrowException(id).removeLink(link);
        LOGGER.info("была удалена ссылка " + link + " у user'а с id = " + id);
    }

    @Override
    public void getUserStatus(long id, UserStatus status) {
        findUserByIdOrThrowException(id).getStatus();
        LOGGER.info("был получен статус user'а с id = " + id);
    }

    @Override
    public void setUserStatus(long id, UserStatus status) {
        findUserByIdOrThrowException(id).setStatus(status);
        LOGGER.info("был изменён статус user'а с id = " + id + " на " + status.name());
    }

    @Override
    public List<LinkModel> getLinks(long id) {
        List<LinkModel> links = findUserByIdOrThrowException(id).getLinksList();
        LOGGER.info("были найдены ссылки" + links + " user'а с id = " + id);
        return links;
    }

    private UserModel findUserByIdOrThrowException(Long id) {
        for (UserModel userModel : userDataSet) {
            if (userModel.getId() == id) {
                return userModel;
            }
        }
        throw new UnknownUserException(id);
    }

    public static class UnknownUserException extends IllegalArgumentException {
        public UnknownUserException(Long id) {
            super("Can't find user with id = " + id);
        }
    }
}
