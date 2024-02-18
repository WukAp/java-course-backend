package edu.java.bot.models;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserModel {

    private final long id;
    private final Set<LinkModel> linksList = new HashSet<>();
    private UserStatus status = UserStatus.WAITING_FOR_COMMAND;

    public UserModel(long userId) {
        this.id = userId;
    }

    public long getId() {
        return id;
    }

    public List<LinkModel> getLinksList() {
        return List.copyOf(linksList);
    }

    public void addNewLink(LinkModel newLink) {
        this.linksList.add(newLink);
    }

    public void removeLink(LinkModel link) {
        linksList.remove(link);
    }

    @Override public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UserModel userModel = (UserModel) object;
        return id == userModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
