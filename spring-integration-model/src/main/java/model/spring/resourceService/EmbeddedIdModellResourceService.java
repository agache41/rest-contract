package model.spring.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.EmbeddedIdModell;
import io.github.agache41.rest.contract.entities.EmbeddedKeys;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@RestController
@RequestMapping("/embeddedIdModell")
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> {

    @Autowired
    protected DataAccess<EmbeddedIdModell, EmbeddedKeys> dataAccess;


    @Autowired
    protected DataBinder<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> dataBinder;

}