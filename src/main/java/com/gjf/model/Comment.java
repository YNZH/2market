package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;

    private Long goodsId;

    private Long userId;

    private String content;

    private String pics;

    private Date timeCreate;

    private Date timeModified;

}