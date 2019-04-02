package com.naadworks.lego.dao;

import com.naadworks.lego.entity.BaseEntity;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;

import java.util.List;
import java.util.Map;

public interface BaseDAO<E extends BaseEntity, ID> {

    E findById(ID id) throws DaoException;

    E create(E var1) throws DaoException;

    E update(E var1, ID var2) throws DaoException;

    PaginatedList<E> query(Map<String, Map<String, String>> var1, String var2, String var3, int var4, int var5) throws DaoException;

    List<E> bulkUpdate(List<E> var1) throws DaoException;

    List<E> bulkCreate(List<E> var1) throws DaoException;


}
