package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/05/13
 * Time   : 12:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long id;
    private Long fromId;
    private Long toId;
    private String fromName;
    private String headerImg;
    private Integer offline;
    private Date createTime;
    private String content;
}
