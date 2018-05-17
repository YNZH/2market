package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: GJF
 * @Date : 2018/05/13
 * Time   : 12:51
 */
@Data
@AllArgsConstructor
public class Message {
    private String fromId;
    private String toId;
    private String fromName;
    private String content;
    private String headerImg;
}
