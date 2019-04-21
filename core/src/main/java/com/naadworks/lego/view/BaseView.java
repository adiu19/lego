package com.naadworks.lego.view;

import com.naadworks.lego.Status;
import com.naadworks.lego.entry.BaseEntry;

import java.util.List;

public class BaseView <T extends BaseEntry> {

    private Status status;

    private List<T> data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}