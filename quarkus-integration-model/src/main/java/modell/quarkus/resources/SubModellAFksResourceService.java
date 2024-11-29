package modell.quarkus.resources;


import io.github.agache41.rest.contract.entities.SubModellAFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
//import modell.quarkus.entities.SubModellAFks;

@ApplicationScoped
@Path("/subModellAFks")
public class SubModellAFksResourceService extends AbstractResourceServiceImpl<SubModellAFks, SubModellAFks, Long> {


}