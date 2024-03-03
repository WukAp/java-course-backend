package edu.java.bot.models;

import edu.java.DTO.LinkUpdateRequest;
import java.net.URI;
import java.net.URISyntaxException;

public record LinkModel(URI link) {
    public LinkModel(LinkUpdateRequest link) {
        this(link.url());
    }

    public LinkModel(String link) throws URISyntaxException {
        this(new URI(link));
    }

    @Override public String toString() {
        return link.toString();
    }
}
