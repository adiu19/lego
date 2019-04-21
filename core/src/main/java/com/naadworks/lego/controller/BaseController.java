package com.naadworks.lego.controller;

import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.view.BaseView;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public interface BaseController<T extends BaseEntry, E extends BaseESEntity, V extends BaseView, ID> {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    V findById(@PathVariable("id") ID id);

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    V create(@RequestBody T t);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    V update(@PathVariable("id") ID id,@RequestBody T t);

    @RequestMapping(value = "/query", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    V query(@RequestParam(value = "start", required = false) int start, @RequestParam(value = "fetchSize", required = false) int fetchSize, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "sortOrder", required =  false) String sortOrder,
            @RequestParam(value = "q", required = false) String query, @RequestParam(value = "f", required = false) Set<String> fields);
}

