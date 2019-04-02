package com.naadworks.lego.dao;

import com.naadworks.lego.entity.BaseEntity;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aditya Upadhyaya on 02/04/19.
 */
public interface ElasticsearchBaseDAO<E extends BaseEntity<ID>, ID extends Serializable> extends BaseDAO<E, ID> {

    PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, Map<String, Map<String, String>> params, Set<String> fields) throws DaoException;

    PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, String searchTerms, Set<String> fields) throws DaoException;

    ElasticsearchOperations getElasticsearchOperations();

    PaginatedList<E> query(SearchQuery query) throws DaoException;

}
