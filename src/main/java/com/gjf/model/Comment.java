package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;

    @NotNull(message = "id不能为空")
    private Long goodsId;
    @NotNull(message = "id不能为空")
    private Long userId;

    /**
     * parent comment id;
     */
    private Long parentId;
    @NotNull(message = "内容不能为空")
    private String content;

    private String pics;

    private Date timeCreate;

    private Date timeModified;

}