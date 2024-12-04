package model.spring.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.ModellFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@RestController
@RequestMapping("/modellFks")
public class ModellFksResourceService extends AbstractResourceServiceImpl<ModellFks, ModellFks, Long> {

    @Autowired
    protected DataAccess<ModellFks, Long> dataAccess;


    @Autowired
    protected DataBinder<ModellFks, ModellFks, Long> dataBinder;
}