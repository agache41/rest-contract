package modell.quarkus.resources;


import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lombok.Getter;
//import modell.quarkus.entities.Modell2;


@Getter
@ApplicationScoped
@Path("/modell2")
public class Modell2ResourceService extends AbstractResourceServiceImpl<Modell2, Modell2, String> {
}