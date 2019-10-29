package com.shp.comb.modle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shp on 17/10/23.
 */
@Data
public class DbBaseObject<T> implements Serializable {


    private T id;

    @JsonIgnore
    private Date create_time;

    @JsonIgnore
    private Date update_time;

}