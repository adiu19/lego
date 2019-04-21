package com.naadworks.lego.dao;

import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseDAO<E extends BaseESEntity, ID> {

    E findById(ID id) throws DaoException;

    E create(E var1) throws DaoException;

    E update(E var1, ID var2) throws DaoException;

    PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, Map<String, Map<String, String>> params, Set<String> fields) throws DaoException;

    PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, String searchTerms, Set<String> fields) throws DaoException;

    List<E> bulkUpdate(List<E> var1) throws DaoException;

    List<E> bulkCreate(List<E> var1) throws DaoException;


}
