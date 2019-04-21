package com.naadworks.lego.service.impl;

import com.naadworks.lego.dao.BaseDAO;
import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.BaseException;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;
import com.naadworks.lego.service.BaseService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
            e = getDao().findById(id);
        } catch (DaoException e1) {
            log.error("error during find by id = ", e1);
        }
        return convertToEntry(e);

    }

    public T create(T t) throws BaseException {
        E e = null;
        try {
            e = getDao().create(convertToEntity(t));
        } catch (DaoException e1) {
            log.error("error while create = ", e1);
        }
        return convertToEntry(e);
    }

    public T update(T t, ID id) throws BaseException{
        E e = null;
        try {
            e = getDao().update(convertToEntity(t), id);
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

    @Override
    public PaginatedList<T> query(int start, int fetchSize, String sortBy, String sortOrder, String query) throws BaseException {
        PaginatedList<T> paginatedEntriesToReturn = new PaginatedList<>();
        PaginatedList<E> paginatedEntitiesToReturn;
        try {
            paginatedEntitiesToReturn = this.getDao().query(start, fetchSize, sortBy, sortOrder, query, null);
        } catch (DaoException e) {
            log.error("error while querying ", e);
            throw new BaseException("Error while Querying");
        }

        List<T> entriesToReturn = new ArrayList<>();
        for (E e : paginatedEntitiesToReturn.getResults())
            entriesToReturn.add(convertToEntry(e));

        paginatedEntriesToReturn.setResults(entriesToReturn);
        paginatedEntriesToReturn.setPageSize(paginatedEntitiesToReturn.getPageSize());
        paginatedEntriesToReturn.setStartIndex(paginatedEntitiesToReturn.getStartIndex());
        paginatedEntriesToReturn.setTotalCount(paginatedEntitiesToReturn.getTotalCount());
        return paginatedEntriesToReturn;
    }

    public abstract T getEntry();

    public abstract E getEntity();
}
