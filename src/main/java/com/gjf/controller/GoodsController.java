package com.gjf.controller;

import com.gjf.config.UploadProperties;
import com.gjf.config.UrlProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.exception.GlobalException;
import com.gjf.mapper.GoodsMapper;
import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Goods;
import com.gjf.model.ResultBean;
import com.gjf.model.User;
import com.gjf.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/26
 * Time   : 16:47
 */
@RestController
@RequestMapping("/api")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private UploadProperties uploadProperties;
    @Autowired
    private UrlProperties urlProperties;
    @Autowired
    private GoodsMapper goodsService;

    @PostMapping("/publish.do")
    public ResultBean publish(Goods goods, @RequestParam("goodsImg") MultipartFile uploadFile, String userId) {
        String path;
        try {
            path = saveUploadSingleImg(uploadFile, Long.valueOf(userId), uploadProperties.getGoodsImgFolder());
        } catch (IOException e) {
            return ResultBean.error(ExceptionEnum.UPLOAD_ERROR);
        }
        goods.setSrc(path);
        goodsService.insert(goods);
        return ResultBean.ok("\"filePath\":\"" + path + "\"");
    }
    @RequestMapping(value = "/goods/category/{category}", method = RequestMethod.GET)
    public ResultBean getGoodsByCategory(@PathVariable String category) {
        logger.info("category===========>" + category);
        if ("newest".equalsIgnoreCase(category)) {
            return ResultBean.ok(goodsService.getRecentlyGoods(category));
        }
        return ResultBean.ok(goodsService.getGoodsByCategory(category));
    }

    @RequestMapping(value = "/goods/{goodsId}",method = RequestMethod.GET)
    public ResultBean getGoodsAndUserByGoodsId(@PathVariable Long goodsId){
        return ResultBean.ok(goodsService.selectGoodsAndUserByGoodsId(goodsId));
    }

    @GetMapping("/goods/list")
    @ApiOperation(value = "获取商品列表")
    public ResultBean getUserList(int page,int limit,String searchKey,String searchValue) {
        Map<String,Object> params = new HashMap<>(4);
        params.put("page",(page-1)*limit);
        params.put("limit",limit);
        if (!StringUtil.isBlank(searchKey,searchValue)){
            params.put("searchKey",searchKey);
            params.put("searchValue",searchValue);
        }
        return ResultBean.ok(goodsService.selectRenderAll(params));
    }

    @RequestMapping(value = "/goods/", method = RequestMethod.PUT)
    @ApiOperation(value = "更新goods", notes = "更新商品信息")
    public ResultBean updateGoodsStatus(@RequestBody Goods goods) {
        goodsService.updateByPrimaryKey(goods);
        return ResultBean.ok();
    }

    private void saveUploadFiles(List<MultipartFile> files) throws IOException {

    }

    /**
     * @param file 上传的商品图片
     * @param uid  上传用户的id
     * @return 上传图片的relativePath 形式为{"uid+File.separator+goodsCount.[jpg|png|其他格式]+UrlConfig.separator"}
     * 对应数据库中图片的src = relativePath1{$url.separator}relativePath2{$url.separator}relativePath3 等
     * 用url.separator分隔路径
     * @throws IOException
     */
    private String saveUploadSingleImg(MultipartFile file, Long uid, String folderType) throws IOException {
        StringBuffer relativePath;
        if (file.isEmpty()) {
            throw new IOException("文件大小不能为空");
        }
        byte[] bytes = file.getBytes();
        relativePath = new StringBuffer();
        relativePath.append(uid).append("/")
                .append(goodsService.getGoodsCountByUserId(uid) + 1L)
                .append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')));
        Path path = Paths.get(folderType, relativePath.toString());
        System.out.println(path);
        if (!Files.exists(path)) {
            if (!Files.exists(path.getParent())) {
                Files.createDirectory(path.getParent());
            }
            Files.createFile(path);
        }
        Files.write(path, bytes);
        return relativePath.append(urlProperties.getSeparator()).toString();
    }
}
