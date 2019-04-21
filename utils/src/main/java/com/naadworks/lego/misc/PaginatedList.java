package com.naadworks.lego.misc;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.util.List;

public class PaginatedList<E> {
    private List<E> results;
    private Integer totalCount;
    private Integer startIndex;
    private Integer pageSize;

    public PaginatedList() {
    }

    public PaginatedList(List<E> results, Integer totalCount, Integer startIndex, Integer pageSize) {
        this.results = results;
        this.totalCount = totalCount;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }

    public List<E> getResults() {
        return this.results;
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaginatedList [results=").append(this.results).append(", totalCount=").append(this.totalCount).append(", startIndex=").append(this.startIndex).append(", pageSize=").append(this.pageSize).append("]");
        return builder.toString();
    }
}
