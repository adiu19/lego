package com.naadworks.lego.controller.impl;

import com.naadworks.lego.Status;
import com.naadworks.lego.controller.BaseController;
import com.naadworks.lego.entity.BaseEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.BaseException;
import com.naadworks.lego.service.impl.BaseServiceImpl;
import com.naadworks.lego.view.BaseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

import static com.naadworks.lego.enums.StatusType.ERROR;
import static com.naadworks.lego.enums.StatusType.SUCCESS;

public abstract class BaseControllerImpl<V extends BaseView, T extends BaseEntry, E extends BaseEntity, ID> implements BaseController<T,E,V,ID> {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
    private BaseServiceImpl<T, E, ID> service;

    public V findById(ID id) {
        V view = this.createResponse(null);
        Status status = new Status(SUCCESS, "Operation is successful");
        List<T> data = new ArrayList<>();

        try {
            T entryFetched = this.getService().findById(id);
            data.add(entryFetched);
            view = this.createResponse(data);
        }
        catch (BaseException be) {
            status.setType(ERROR);
        }
        view.setStatus(status);
        return view;

    }

    public V create(T t) {
        V view = this.createResponse(null);
        Status status = new Status(SUCCESS, "Operation is successful");
        List<T> data = new ArrayList<>();
        try {
            T entryCreated = this.getService().create(t);
            data.add(entryCreated);
            view = this.createResponse(data);
        }
        catch (BaseException be) {
            status.setType(ERROR);
        }
        view.setStatus(status);
        return view;
    }

    public V update(ID id, T t) {
        V view = this.createResponse(null);
        Status status = new Status(SUCCESS, "Operation is successful");
        List<T> data = new ArrayList<>();
        try {
            T entryUpdate = this.getService().update(id, t);
            data.add(entryUpdate);
            view = this.createResponse(data);
        }
        catch(BaseException be) {
            status.setType(ERROR);
        }
        view.setStatus(status);
        return view;

    }

    public BaseServiceImpl<T, E, ID> getService() {
        return service;
    }

    public void setService(BaseServiceImpl<T, E, ID> service) {
        this.service = service;
    }

    public abstract V createResponse(List<T> var1);

}
