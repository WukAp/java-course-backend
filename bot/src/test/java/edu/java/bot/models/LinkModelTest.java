package edu.java.bot.models;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class LinkModelTest {
    private final LinkModel linkModel = new LinkModel("testLink");

    @Test
    void testToString() {
        assertEquals("testLink", linkModel.toString());
    }

    @Test
    void link() {
        assertEquals("testLink", linkModel.link());
    }
}
