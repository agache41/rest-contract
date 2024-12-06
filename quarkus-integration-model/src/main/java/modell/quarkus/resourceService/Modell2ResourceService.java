package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.ws.rs.Path;

@Path("/modell2")
public class Modell2ResourceService extends AbstractResourceServiceImpl<Modell2, Modell2, String> {
}