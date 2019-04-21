package com.naadworks.helpers;

import java.util.Map;
import java.util.Map.Entry;

import com.naadworks.lego.enums.QueryOperators;
import com.naadworks.lego.misc.QueryParser;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class ElasticsearchQueryHelper {

    public static QueryBuilder buildQuery(Map<String, Map<String, String>> searchParams) {
        if(searchParams.isEmpty()) {
            return QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            for (Entry<String, Map<String, String>> entry : searchParams.entrySet()) {
                for (Entry<String, String> e : entry.getValue().entrySet()) {
                    addPredicate(queryBuilder, entry.getKey(), e.getKey(), e.getValue());
                }
            }
            return queryBuilder;
        }
    }

    public static SortOrder getSortOrder(String sortOrder) {
        if (sortOrder.equalsIgnoreCase("ASC")) {
            return SortOrder.ASC;
        } else {
            return SortOrder.DESC;
        }
    }

    private static void addPredicate(BoolQueryBuilder queryBuilder, String operator, String name, String value) {

        switch (QueryOperators.fromString(operator)) {
            case SUFFIX_EQUAL_TO: {
                queryBuilder.must(QueryBuilders.termQuery(name, value));
                break;
            }
            case SUFFIX_IS_NULL:
                queryBuilder.mustNot(QueryBuilders.existsQuery(name));
                break;
            case SUFFIX_IS_NOT_NULL:
                queryBuilder.must(QueryBuilders.existsQuery(name));
                break;
            case SUFFIX_NOT_EQUAL_TO: {
                queryBuilder.mustNot(QueryBuilders.termQuery(name, value));
                break;
            }
            case SUFFIX_GREATER_THAN: {
                QueryBuilder filter = QueryBuilders.rangeQuery(name).gt(value);
                queryBuilder.must(filter);
                break;
            }
            case SUFFIX_GREATER_THAN_EQUAL_TO: {
                QueryBuilder filter = QueryBuilders.rangeQuery(name).gte(value);
                queryBuilder.must(filter);
                break;
            }
            case SUFFIX_LESS_THAN: {
                QueryBuilder filter = QueryBuilders.rangeQuery(name).lt(value);
                queryBuilder.must(filter);
                break;
            }
            case SUFFIX_LESS_THAN_EQUAL_TO: {
                QueryBuilder filter = QueryBuilders.rangeQuery(name).lte(value);
                queryBuilder.must(filter);
                break;
            }
            case SUFFIX_LIKE: {
                queryBuilder.must(QueryBuilders.prefixQuery(name, value));
                break;
            }
            case SUFFIX_IN: {
                Object[] values = value.split(QueryParser.IN_VALUES_DELIMETER);
                queryBuilder.must(QueryBuilders.termsQuery(name, values));
                break;
            }
            case SUFFIX_NOT_IN: {
                Object[] values = value.split(QueryParser.IN_VALUES_DELIMETER);
                queryBuilder.mustNot(QueryBuilders.termsQuery(name, values));
                break;
            }
            default: {
                queryBuilder.must(QueryBuilders.termQuery(name, value));
                break;
            }
        }

    }

}
