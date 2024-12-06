package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.ModellFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.ws.rs.Path;

@Path("/modellFks")
public class ModellFksResourceService extends AbstractResourceServiceImpl<ModellFks, ModellFks, Long> {
}