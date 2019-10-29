package com.shp.comb.modle.common;

import com.shp.comb.modle.DbBaseObject;
import lombok.Data;

import java.util.Map;

/**
 * Created by shp on 18/1/25.
 */
@Data
public class EmailDto extends DbBaseObject<Integer> {

    private String from;
    private String fromName;
    private String[] toEmails;

    private String subject;

    private Map data ; 			//邮件数据
    private String template;	//邮件模板
}
