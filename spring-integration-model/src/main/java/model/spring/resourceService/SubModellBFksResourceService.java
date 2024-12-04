package model.spring.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.SubModellAFks;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequestMapping("/subModellBFks")
public class SubModellBFksResourceService extends AbstractResourceServiceImpl<SubModellAFks, SubModellAFks, Long> {
    @Autowired
    protected DataAccess<SubModellAFks, Long> dataAccess;


    @Autowired
    protected DataBinder<SubModellAFks, SubModellAFks, Long> dataBinder;

}