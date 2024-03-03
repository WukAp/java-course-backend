package edu.java.client.trackingClients;

import edu.java.models.RelativeLinkModel;
import java.time.OffsetDateTime;
import reactor.core.publisher.Mono;

public abstract class Client {
    public abstract RelativeLinkModel createLinkModel(String link);

    public abstract Mono<OffsetDateTime> getLastModifier(RelativeLinkModel linkModel);
}
