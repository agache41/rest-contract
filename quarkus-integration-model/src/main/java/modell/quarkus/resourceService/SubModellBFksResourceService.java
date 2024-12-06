package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.SubModellAFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.ws.rs.Path;

@Path("/subModellBFks")
public class SubModellBFksResourceService extends AbstractResourceServiceImpl<SubModellAFks, SubModellAFks, Long> {
}