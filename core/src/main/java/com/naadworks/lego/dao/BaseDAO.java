package com.naadworks.lego.dao;

import com.naadworks.lego.entity.BaseEntity;
import com.naadworks.lego.exceptions.BaseException;

public interface BaseDAO<E extends BaseEntity, ID> {

    public E findById(ID id) throws BaseException;
}
