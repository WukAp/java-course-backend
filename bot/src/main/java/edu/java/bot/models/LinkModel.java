package edu.java.bot.models;

public record LinkModel(String link) {
    @Override public String toString() {
        return link;
    }
}
