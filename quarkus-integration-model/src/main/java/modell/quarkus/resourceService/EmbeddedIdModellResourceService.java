package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.EmbeddedIdModell;
import io.github.agache41.rest.contract.entities.EmbeddedKeys;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lombok.Getter;
//import modell.quarkus.entities.EmbeddedIdModell;
//import modell.quarkus.entities.EmbeddedKeys;


@Getter
@ApplicationScoped
@Path("/embeddedIdModell")
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> {

}