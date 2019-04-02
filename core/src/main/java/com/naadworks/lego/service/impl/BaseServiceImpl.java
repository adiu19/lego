package com.naadworks.lego.service.impl;

import com.naadworks.lego.dao.BaseDAO;
import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.BaseException;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.service.BaseService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl<T extends BaseEntry, E extends BaseESEntity, ID> implements BaseService<T, E, ID> {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    private BaseDAO<E, ID> dao;

    public BaseDAO<E, ID> getDao() {
        return dao;
    }

    public void setDao(BaseDAO<E, ID> dao) {
        this.dao = dao;
    }

    public T findById(ID id) throws BaseException {
        E e = null;
        try {
            e = dao.findById(id);
        } catch (DaoException e1) {
            log.error("error during find by id = ", e1);
        }
        return convertToEntry(e);

    }

    public T create(T t) throws BaseException {
        E e = null;
        try {
            e = dao.create(convertToEntity(t));
        } catch (DaoException e1) {
            log.error("error while create = ", e1);
        }
        return convertToEntry(e);
    }

    public T update(ID id, T t) throws BaseException{
        E e = null;
        try {
            e = dao.update(convertToEntity(t), id);
        } catch (DaoException e1) {
            log.error("error while update = ",e1);
        }
        return convertToEntry(e);
    }

    public T convertToEntry(E e) {
        T t = null;
        if (e != null) {
            t = this.getEntry();
            try {
                BeanUtils.copyProperties(t, e);
            }catch (Exception ex) {
                log.error("error while converting to entry = ", ex);
                return null;
            }
        }
        return t;
    }

    public E convertToEntity(T t) {
        E e = null;
        if (t != null) {
            e = this.getEntity();
            try {
                BeanUtils.copyProperties(e, t);
            }catch (Exception ex) {
                log.error("error while converting to entity = ", ex);
                return null;
            }
        }
        return e;
    }
    public abstract T getEntry();

    public abstract E getEntity();
}
