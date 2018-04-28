package com.gjf.controller;

import com.gjf.config.UploadProperties;
import com.gjf.config.UrlProperties;
import com.gjf.mapper.GoodsMapper;
import com.gjf.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author: GJF
 * @Date : 2018/04/26
 * Time   : 16:47
 */
@RestController
@RequestMapping("/api")
public class PublishController {

    @Autowired
    private UploadProperties uploadProperties;
    @Autowired
    private UrlProperties urlProperties;
    @Autowired
    private GoodsMapper goodsService;

    @PostMapping("/publish.do")
    public ResponseEntity<?> publish(Goods goods, @RequestParam("goodsImg") MultipartFile uploadFile) {
        String path = "";
        System.out.println(goods);
        try {
            path = saveUploadSingleImg(uploadFile, 1L, uploadProperties.getGoodsImgFolder());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("\"filePath\":\"" + path + "\"");
    }

    @GetMapping("/test")
    public String test() {
        return "\"test\":\"valueTest\"";
    }

    private void saveUploadFiles(List<MultipartFile> files) throws IOException {

    }

    /**
     *
     * @param file 上传的商品图片
     * @param uid  上传用户的id
     * @return     上传图片的relativePath 形式为{"uid+File.separator+goodsCount.[jpg|png|其他格式]+UrlConfig.separator"}
     *              对应数据库中图片的src = relativePath1{$url.separator}relativePath2{$url.separator}relativePath3 等
     *              用url.separator分隔路径
     * @throws IOException
     */
    private String saveUploadSingleImg(MultipartFile file, Long uid,String folderType) throws IOException {
        StringBuffer relativePath;
        if (file.isEmpty()) {
            throw new IOException("文件大小不能为空");
        }
        byte[] bytes = file.getBytes();
        relativePath = new StringBuffer();
        relativePath.append(uid).append(File.separator)
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
