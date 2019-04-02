package com.naadworks.lego.dao.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.naadworks.helpers.ElasticsearchQueryHelper;
import com.naadworks.lego.dao.ElasticsearchBaseDAO;
import com.naadworks.lego.entity.BaseESEntity;
import com.naadworks.lego.exceptions.DaoException;
import com.naadworks.lego.misc.PaginatedList;
import com.naadworks.lego.misc.QueryParser;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Parent;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.Assert;

public abstract class ElasticsearchBaseDAOImpl<E extends BaseESEntity<ID>, ID extends Serializable> implements ElasticsearchBaseDAO<E, ID> {

    protected static final Logger log = LoggerFactory.getLogger(ElasticsearchBaseDAOImpl.class);

    @Autowired(required = false)
    @Qualifier("elasticsearchTemplate")
    protected ElasticsearchOperations elasticsearchOperations;

    protected Class<E> entityClass;
    private String indexName;
    private String docType;

    public ElasticsearchOperations getElasticsearchOperations() {
        return elasticsearchOperations;
    }

    public void setElasticsearchOperations(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public ElasticsearchBaseDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        Document document = this.entityClass.getAnnotation(Document.class);
        this.indexName = document.indexName();
        this.docType = document.type();
    }

    @Override
    public E create(E entity) throws DaoException {
        Assert.notNull(entity, "Cannot save 'null' entity.");
        IndexQuery query = new IndexQuery();
        query.setObject(entity);
        query.setIndexName(indexName);
        query.setType(docType);
        if (entity.getId() != null) {
            query.setId(entity.getId().toString());
        }
        Field parentField = this.getParentField();
        if (parentField != null) {
            query.setParentId(this.getParentId(entity, parentField));
        }
        elasticsearchOperations.index(query);
        return entity;
    }

    @Override
    public E update(E entity, ID id) throws DaoException {
        Assert.notNull(entity, "Cannot save 'null' entity.");

        IndexQuery query = new IndexQuery();
        query.setObject(entity);
        query.setIndexName(indexName);
        query.setType(docType);
        if (entity.getId() != null) {
            query.setId(entity.getId().toString());
        }
        Field parentField = this.getParentField();
        if (parentField != null) {
            query.setParentId(this.getParentId(entity, parentField));
        }
        elasticsearchOperations.index(query);
        return entity;
    }

    @Override
    public E findById(ID id) {
        if (id == null)
            return null;

        GetQuery query = new GetQuery();
        query.setId(id.toString());
        return elasticsearchOperations.queryForObject(query, entityClass);

    }

    @Override
    public List<E> bulkUpdate(List<E> entities) throws DaoException {
        Assert.notNull(entities, "Cannot insert 'null' as a List.");
        Assert.notEmpty(entities, "Cannot insert empty List.");
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        Field parentField = this.getParentField();
        for (E e : entities) {
            IndexQuery query = new IndexQuery();
            query.setObject(e);
            query.setIndexName(indexName);
            query.setType(docType);
            if (e.getId() != null) {
                query.setId(e.getId().toString());
            }
            if (parentField != null) {
                query.setParentId(this.getParentId(e, parentField));
            }
            queries.add(query);
        }
        elasticsearchOperations.bulkIndex(queries);
        return entities;
    }

    @Override
    public List<E> bulkCreate(List<E> entities) throws DaoException {
        Assert.notNull(entities, "Cannot insert 'null' as a List.");
        Assert.notEmpty(entities, "Cannot insert empty List.");
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        Field parentField = this.getParentField();
        for (E e : entities) {
            IndexQuery query = new IndexQuery();
            query.setObject(e);
            query.setIndexName(indexName);
            query.setType(docType);
            if (e.getId() != null) {
                query.setId(e.getId().toString());
            }
            if (parentField != null) {
                query.setParentId(this.getParentId(e, parentField));
            }
            queries.add(query);
        }
        elasticsearchOperations.bulkIndex(queries);
        return entities;
    }

    @Override
    public PaginatedList<E> query(SearchQuery query) {
        Page<E> pages = elasticsearchOperations.queryForPage(query, entityClass);

        PaginatedList<E> results = new PaginatedList<E>();

        if (pages != null && pages.hasContent()) {
            results.setResults(pages.getContent());
            results.setPageSize(pages.getNumberOfElements());
            results.setTotalCount(pages.getTotalPages());
        }

        return results;
    }

    private Field getParentField() {
        /**
         * Find the field marked with Parent annotation
         */
        for (Field field : this.entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parent.class)) {
                return field;
            }
        }
        return null;
    }

    private String getParentId(E entity, Field parentField) {
        String parentId = null;
        try {
            // TODO check if the casting is correct
            parentId = (String) new PropertyDescriptor(parentField.getName(), this.entityClass).getReadMethod().invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            log.error("Failed to invoke getter method for Parent ID of " + this.entityClass.getName());
        }
        return parentId;
    }

    @Override
    public PaginatedList<E> query(Map<String, Map<String, String>> params, String sortBy, String sortOrder, int start, int fetchSize) throws DaoException {
        return query(start, fetchSize, sortBy, sortOrder, params, null);
    }

    @Override
    public PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, Map<String, Map<String, String>> params, Set<String> fields) {
        BoolQueryBuilder filteredQuery;
        if(!params.isEmpty())
            filteredQuery = (BoolQueryBuilder) ElasticsearchQueryHelper.buildQuery(params);
        else
            filteredQuery = QueryBuilders.boolQuery();

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).withFilter(filteredQuery);

        if(fields != null)
            for(String op : params.keySet())
                for(String key : params.get(op).keySet())
                    fields.add(key);

        return buildAndExecuteSearch(start, fetchSize, sortBy, sortOrder, fields, nativeSearchQueryBuilder);
    }


    @Override
    public PaginatedList<E> query(int start, int fetchSize, String sortBy, String sortOrder, String searchTerms, Set<String> fields) throws DaoException{
        Map<String, Map<String, String>> params;
        try{
            params = QueryParser.parseQuery(searchTerms);
        } catch (UnsupportedEncodingException e) {
            throw new DaoException();
        }
        return query(start, fetchSize, sortBy, sortOrder, params, fields);
    }

    public PaginatedList<E> buildAndExecuteSearch(int start, int fetchSize, String sortBy, String sortOrder, Set<String> fields, NativeSearchQueryBuilder nativeSearchQueryBuilder){
        SearchQuery query;

        if(fields != null)
            nativeSearchQueryBuilder.withFields(fields.toArray(new String[fields.size()]));

        Pageable page;
        if(fetchSize != -1) {
            int pageNo = start / fetchSize;
            page = new PageRequest(pageNo, fetchSize);
            nativeSearchQueryBuilder.withPageable(page);
        } else {
            page = new PageRequest(0, 10000);       //after this size, we have to use scan and scroll
            nativeSearchQueryBuilder.withPageable(page);
        }

        if(!StringUtils.isEmpty(sortBy) && !sortBy.equals("NOSORT"))
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(ElasticsearchQueryHelper.getSortOrder(sortOrder)));

        query = nativeSearchQueryBuilder.build();

        Page<E> pages = elasticsearchOperations.queryForPage(query, entityClass);
        PaginatedList<E> results = new PaginatedList<>();

        if (pages != null && pages.hasContent()) {
            results.setResults(pages.getContent());
            results.setStartIndex(start);
            results.setPageSize(pages.getNumberOfElements());
            results.setTotalCount((int)pages.getTotalElements());
        } else {
            results.setTotalCount(0);
            results.setResults(new ArrayList<E>());
        }
        return results;
    }
}
