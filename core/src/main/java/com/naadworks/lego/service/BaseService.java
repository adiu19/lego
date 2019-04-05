package com.naadworks.lego.service;

import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.entry.BaseEntry;
import com.naadworks.lego.exceptions.BaseException;
import com.naadworks.lego.misc.PaginatedList;

/**
 * Created by Aditya Upadhyaya on 02/04/19.
 */
public interface BaseService <T extends BaseEntry, E extends BaseESEntity, ID> {

    T findById(ID var1) throws BaseException;

    T create(T var1) throws BaseException;

    T update(T var1, ID var2) throws BaseException;

    PaginatedList<T> query(int start, int fetchSize, String sortBy, String sortOrder, String query) throws BaseException;

    E convertToEntity(T var1);

    T convertToEntry(E var1);
}
