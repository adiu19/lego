package com.naadworks.lego.controller.impl;

import com.naadworks.lego.Status;
import com.naadworks.lego.entity.BaseEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.BaseException;
import com.naadworks.lego.service.impl.BaseServiceImpl;
import com.naadworks.lego.view.BaseView;


import java.util.ArrayList;
import java.util.List;

import static com.naadworks.lego.enums.StatusType.ERROR;
import static com.naadworks.lego.enums.StatusType.SUCCESS;

public abstract class BaseControllerImpl<V extends BaseView, T extends BaseEntry, E extends BaseEntity> {

    private BaseServiceImpl<T, E> service;

    public BaseServiceImpl<T, E> getService() {
        return service;
    }

    public void setService(BaseServiceImpl<T, E> service) {
        this.service = service;
    }

    public V findById(String id) {
        V view = this.createResponse(null);
        Status status = new Status(SUCCESS, "Operation is successful");
        List<T> data = new ArrayList<>();

        try {
            T entryFetched = this.getService().getById(id);
            data.add(entryFetched);
            view = this.createResponse(data);
        }
        catch (BaseException be) {
            status.setType(ERROR);
        }
        view.setStatus(status);
        return view;

    }

    public abstract V createResponse(List<T> var1);

}
