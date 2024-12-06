package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.EmbeddedIdModell;
import io.github.agache41.rest.contract.entities.EmbeddedKeys;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.ws.rs.Path;


@Path("/embeddedIdModell")
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> {
}