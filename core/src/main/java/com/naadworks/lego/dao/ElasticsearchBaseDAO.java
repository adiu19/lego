package com.naadworks.lego.dao;

import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aditya Upadhyaya on 02/04/19.
 */

public interface ElasticsearchBaseDAO<E extends BaseESEntity<ID>, ID extends Serializable> extends BaseDAO<E, ID> {

    ElasticsearchOperations getElasticsearchOperations();

    PaginatedList<E> query(SearchQuery query) throws DaoException;

    List<E> bulkUpdate(List<E> var1) throws DaoException;

    List<E> bulkCreate(List<E> var1) throws DaoException;

}
