package com.naadworks.lego.controller;

import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.view.BaseView;
import org.springframework.web.bind.annotation.*;

@RestController
public interface BaseController<T extends BaseEntry, E extends BaseESEntity, V extends BaseView, ID> {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    V findById(@PathVariable("id") ID id);

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    V create(@RequestBody T t);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    V update(@PathVariable("id") ID id,@RequestBody T t);
}

