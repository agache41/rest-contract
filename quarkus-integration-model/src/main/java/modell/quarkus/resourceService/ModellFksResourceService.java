package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.ModellFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
//import modell.quarkus.entities.ModellFks;


@ApplicationScoped
@Path("/modellFks")
public class ModellFksResourceService extends AbstractResourceServiceImpl<ModellFks, ModellFks, Long> {


}