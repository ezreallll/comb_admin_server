package com.shp.comb.service.common;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by shp on 17/5/10.
 */
@Service
public class UploadFileService {

    @Value("${IMG_SAVE_PATH}")
    private String IMG_SAVE_PATH;


    /**
     * 保存头像
     * @param img
     * @return
     * @throws IOException
     */
    public String saveAvatarImg(MultipartFile img) throws IOException {
        String savePath = "/data/" + getDatePath();
        String savePathLocal = IMG_SAVE_PATH + savePath;
        String fileName=save(savePathLocal, img, UUID.randomUUID().toString().replace("-",""));
        return savePath+"/"+fileName;
    }


    /**
     * 保存文件
     * @param savePath
     * @param multipartFile
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    private String save(String savePath, MultipartFile multipartFile,String fileName) throws IOException {
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String oldFileName= multipartFile.getOriginalFilename();
        String suffix =oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName=fileName+suffix;
        File imgFile = new File(file,newFileName);
        FileUtils.writeByteArrayToFile(imgFile, multipartFile.getBytes());
        return newFileName;
    }

    /**
     * 保存文件  默认上传文件名
     * @param savePath
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private String save(String savePath, MultipartFile multipartFile) throws IOException {
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imgFile = new File(file, multipartFile.getOriginalFilename());
        FileUtils.writeByteArrayToFile(imgFile, multipartFile.getBytes());
        return multipartFile.getOriginalFilename();
    }


    private String getDatePath() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

}


