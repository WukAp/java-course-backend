package edu.java.DTO;

import java.net.URI;

public record LinkUpdateRequest(URI url, String description, int tgChatId) {
}
