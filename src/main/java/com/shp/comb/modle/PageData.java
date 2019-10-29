package com.shp.comb.modle;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shp on 19/10/28.
 */
@Data
public class PageData<T> implements Serializable {

    private List<T> list;
    private int page_index;
    private int page_size;
    private long total_count;
    private boolean is_last_page;


    public PageData<T> convertPageData(List<T> list) {
        PageData<T> pageData = new PageData<T>();
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(list);
        set_last_page(pageInfo.isIsLastPage());
        setPage_index(pageInfo.getPageNum());
        setPage_size(pageInfo.getPageSize());
        setTotal_count(pageInfo.getTotal());
        this.list = list;
        return pageData;
    }

}
