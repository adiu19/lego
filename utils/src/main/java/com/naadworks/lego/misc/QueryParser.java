package com.naadworks.lego.misc;

import com.naadworks.lego.enums.QueryOperators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;



public final class QueryParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryParser.class);
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String SEARCH_DELIMETER = "___";
    public static final String KEY_VALUE_DELIMETER = ":";
    public static final String ATTR_OPRERATOR_DELIMETER = ".";
    public static final String IN_VALUES_DELIMETER = ",";
    public static final String SUFFIX_EQUAL_TO = "eq";
    public static final String SUFFIX_IS_NULL = "is";
    public static final String SUFFIX_IS_NOT_NULL = "nn";
    public static final String SUFFIX_NOT_EQUAL_TO = "ne";
    public static final String SUFFIX_GREATER_THAN = "gt";
    public static final String SUFFIX_LESS_THAN = "lt";
    public static final String SUFFIX_GREATER_THAN_EQUAL_TO = "ge";
    public static final String SUFFIX_LESS_THAN_EQUAL_TO = "le";
    public static final String SUFFIX_LIKE = "like";
    public static final String SUFFIX_IN = "in";
    public static final String SUFFIX_NOT_IN = "nin";
    public static final String searchPattern = "(?<=:)(?:(?!:|___).)+";
    public static final RegexRewriter regexRewriter = new RegexRewriter("(?<=:)(?:(?!:|___).)+") {
        public String replacement() {
            try {
                return URLEncoder.encode(this.group(0), "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                return this.group(0);
            }
        }
    };

    private QueryParser() {
    }

    public static Map<String, Map<String, String>> parseQuery(String searchTerms) throws UnsupportedEncodingException {
        Map<String, Map<String, String>> params = new HashMap<>();
        Map<String, String> eqParams = new HashMap<>();
        Map<String, String> isNullParams = new HashMap<>();
        Map<String, String> isNotNullParams = new HashMap<>();
        Map<String, String> neParams = new HashMap<>();
        Map<String, String> ltParams = new HashMap<>();
        Map<String, String> gtParams = new HashMap<>();
        Map<String, String> leParams = new HashMap<>();
        Map<String, String> geParams = new HashMap<>();
        Map<String, String> likeParams = new HashMap<>();
        Map<String, String> inParams = new HashMap<>();
        Map<String, String> notInParams = new HashMap<>();
        if (null != searchTerms && searchTerms.length() > 0) {
            String[] pairs = searchTerms.split("___");
            String[] var14 = pairs;
            int var15 = pairs.length;

            for(int var16 = 0; var16 < var15; ++var16) {
                String pair = var14[var16];
                int pos = pair.indexOf(":");
                String name;
                String value;
                if (pos == -1) {
                    name = pair;
                    value = null;
                } else {
                    name = URLDecoder.decode(pair.substring(0, pos), "UTF-8");
                    value = URLDecoder.decode(pair.substring(pos + 1, pair.length()), "UTF-8");
                }

                int oPos = name.lastIndexOf(".");
                String operator = null;
                if (oPos > 0) {
                    operator = name.substring(oPos + 1);
                }

                if (null != operator && operator.length() != 0) {
                    QueryOperators op = QueryOperators.fromString(operator);
                    switch(op) {
                        case SUFFIX_EQUAL_TO:
                            eqParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_IS_NULL:
                            isNullParams.put(name.substring(0, oPos), null);
                            break;
                        case SUFFIX_IS_NOT_NULL:
                            isNotNullParams.put(name.substring(0, oPos), null);
                            break;
                        case SUFFIX_NOT_EQUAL_TO:
                            neParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_GREATER_THAN:
                            gtParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_GREATER_THAN_EQUAL_TO:
                            geParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_LESS_THAN:
                            ltParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_LESS_THAN_EQUAL_TO:
                            leParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_LIKE:
                            likeParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_IN:
                            inParams.put(name.substring(0, oPos), value);
                            break;
                        case SUFFIX_NOT_IN:
                            notInParams.put(name.substring(0, oPos), value);
                            break;
                        default:
                            eqParams.put(name.substring(0, oPos), value);
                    }
                } else {
                    eqParams.put(name, value);
                }

                params.put(QueryOperators.SUFFIX_EQUAL_TO.msg, eqParams);
                params.put(QueryOperators.SUFFIX_IS_NULL.msg, isNullParams);
                params.put(QueryOperators.SUFFIX_IS_NOT_NULL.msg, isNotNullParams);
                params.put(QueryOperators.SUFFIX_NOT_EQUAL_TO.msg, neParams);
                params.put(QueryOperators.SUFFIX_GREATER_THAN.msg, gtParams);
                params.put(QueryOperators.SUFFIX_GREATER_THAN_EQUAL_TO.msg, geParams);
                params.put(QueryOperators.SUFFIX_LESS_THAN.msg, ltParams);
                params.put(QueryOperators.SUFFIX_LESS_THAN_EQUAL_TO.msg, leParams);
                params.put(QueryOperators.SUFFIX_LIKE.msg, likeParams);
                params.put(QueryOperators.SUFFIX_IN.msg, inParams);
                params.put(QueryOperators.SUFFIX_NOT_IN.msg, notInParams);
            }
        }

        return params;
    }

    public static String encodeSearchParams(String searchParams) {
        return regexRewriter.rewrite(searchParams);
    }
}