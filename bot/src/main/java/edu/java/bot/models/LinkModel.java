package edu.java.bot.models;

import edu.java.DTO.LinkUpdateRequest;

public record LinkModel(String link) {
    public LinkModel(LinkUpdateRequest link) {
        this(link.url().toString());
    }

    @Override public String toString() {
        return link;
    }
}
