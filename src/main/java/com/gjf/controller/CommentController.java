package com.gjf.controller;

import com.gjf.mapper.CommentMapper;
import com.gjf.model.Comment;
import com.gjf.model.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @Author: GJF
 * @Date : 2018/05/10
 * Time   : 21:57
 */
@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentService;

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResultBean addComment(@Valid Comment comment){
        log.info(comment.toString());
        commentService.insert(comment);
        return ResultBean.ok(comment.getId()+" parentID default = "+comment.getParentId());
    }

    @RequestMapping(value = "/list/{goodsId}",method = RequestMethod.GET)
    public ResultBean listCommentByGoodsId(@PathVariable Long goodsId){
        return ResultBean.ok(commentService.listCommentsByGoodsId(goodsId));
    }
}
