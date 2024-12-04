package model.spring.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@RestController
@RequestMapping("/modell2")
public class Modell2ResourceService extends AbstractResourceServiceImpl<Modell2, Modell2, String> {
    @Autowired
    protected DataAccess<Modell2, String> dataAccess;


    @Autowired
    protected DataBinder<Modell2, Modell2, String> dataBinder;
}