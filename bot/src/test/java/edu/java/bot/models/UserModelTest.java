package edu.java.bot.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {
    private final UserModel simpleUserModel0 = new UserModel(0);
    private final UserModel simpleUserModel1 = new UserModel(1);
    private final UserModel simpleUserModel2 = new UserModel(2);
    private UserModel userModelForEach;
    private final LinkModel linkModel0 = new LinkModel("test0");
    private final LinkModel linkModel1 = new LinkModel("test1");
    private final LinkModel linkModel2 = new LinkModel("test3");

    UserModelTest() throws URISyntaxException {
    }

    @BeforeEach
    void setUp() {
        userModelForEach = new UserModel(0);
    }

    @Test
    void getId() {
        assertEquals(0, simpleUserModel0.getId());
        assertEquals(1, simpleUserModel1.getId());
        assertEquals(2, simpleUserModel2.getId());
    }

    @Test
    void addNewLink() {

        assertEquals(0, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.addNewLink(linkModel0));
        assertEquals(1, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.addNewLink(linkModel1));
        assertEquals(2, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.addNewLink(linkModel1));
        assertEquals(2, userModelForEach.getLinksList().size());
    }

    @Test
    void getLinksList() {
        assertEquals(0, userModelForEach.getLinksList().size());
        userModelForEach.addNewLink(linkModel0);
        userModelForEach.addNewLink(linkModel1);
        assertArrayEquals(new Object[]{linkModel0, linkModel1}, userModelForEach.getLinksList().toArray());
    }

    @Test
    void removeLink() {
        assertEquals(0, userModelForEach.getLinksList().size());
        userModelForEach.addNewLink(linkModel0);
        userModelForEach.addNewLink(linkModel1);
        userModelForEach.addNewLink(linkModel2);
        assertEquals(3, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.removeLink(linkModel0));
        assertEquals(2, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.removeLink(linkModel0));
        assertEquals(2, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.removeLink(linkModel1));
        assertEquals(1, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.removeLink(linkModel2));
        assertEquals(0, userModelForEach.getLinksList().size());

        assertDoesNotThrow(() -> userModelForEach.removeLink(linkModel2));
        assertEquals(0, userModelForEach.getLinksList().size());
    }

    @Test
    void testEquals() {
        assertEquals(userModelForEach, userModelForEach);
        assertEquals(simpleUserModel0, userModelForEach);

        userModelForEach.addNewLink(linkModel0);
        userModelForEach.addNewLink(linkModel1);
        userModelForEach.addNewLink(linkModel2);
        assertEquals(simpleUserModel0, userModelForEach);

        assertNotEquals(simpleUserModel0, simpleUserModel1);
        assertNotEquals(simpleUserModel0, 0);
    }

    @Test
    void testHashCode() {
        assertEquals(simpleUserModel0.hashCode(), userModelForEach.hashCode());

        userModelForEach.addNewLink(linkModel0);
        userModelForEach.addNewLink(linkModel1);
        userModelForEach.addNewLink(linkModel2);
        assertEquals(simpleUserModel0.hashCode(), userModelForEach.hashCode());

        assertNotEquals(simpleUserModel0.hashCode(), simpleUserModel1.hashCode());
    }

    @Test
    void getAndSetStatus() {
        assertEquals(UserStatus.WAITING_FOR_COMMAND, userModelForEach.getStatus());

        userModelForEach.setStatus(UserStatus.WAITING_FOR_TRACKING_LINK);
        assertEquals(UserStatus.WAITING_FOR_TRACKING_LINK, userModelForEach.getStatus());

        userModelForEach.setStatus(UserStatus.WAITING_FOR_UNTRACKING_LINK);
        assertEquals(UserStatus.WAITING_FOR_UNTRACKING_LINK, userModelForEach.getStatus());

        userModelForEach.setStatus(UserStatus.WAITING_FOR_COMMAND);
        assertEquals(UserStatus.WAITING_FOR_COMMAND, userModelForEach.getStatus());
    }
}
