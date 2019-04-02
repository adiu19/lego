package com.naadworks.lego;

import com.naadworks.lego.enums.StatusType;

public class Status {

    private StatusType type;

    private  String message;

    public Status() {
    }

    public Status(StatusType type, String message) {
        this.type = type;
        this.message = message;
    }

    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "temp.Status{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
