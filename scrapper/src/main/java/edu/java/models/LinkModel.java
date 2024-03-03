package edu.java.models;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public record LinkModel(URI url, String description) {
    public LinkModel(String url, String description) throws URISyntaxException {
        this(new URI(url), description);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkModel model = (LinkModel) o;
        return Objects.equals(url, model.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
