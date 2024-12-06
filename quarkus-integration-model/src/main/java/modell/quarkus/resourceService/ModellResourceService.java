package modell.quarkus.resourceService;


import io.github.agache41.rest.contract.entities.Modell;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import modell.quarkus.dataAccess.ModellDataAccess;

import java.util.List;


@Getter
@Path("/modell")
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Modell, Long> {

    @Inject
    ModellDataAccess dataAccess;

    /**
     * Finds and returns all the models over 100
     *
     * @return the models list.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/over/100")
    public List<Modell> getOver100AsList() {
        return getDataAccess().getAllModellsOver100();
    }
}