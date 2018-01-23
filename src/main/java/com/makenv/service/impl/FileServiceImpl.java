package com.makenv.service.impl;

import com.google.common.collect.Lists;
import com.makenv.service.FileService;
import com.makenv.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String upload(MultipartFile file, String path) {

        //得到上传的文件名
        String fileName = file.getOriginalFilename();
        //的到扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //重新生成fileName是为了防止覆盖
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        //{}是占位符
        logger.info("开始上传文件，上传的文件名:{}，上传路径:{}，新文件名为:{}", fileName, path, uploadFileName);

        //创建所需的文件
        File fileDir = new File(path);
        if (! fileDir.exists()) {
            fileDir.setWritable(true);
            //创建该目录下的抽象路径名命名，包括任何必要的但不存在父目录。
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件上传成功
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //文件上传到ftp服务器
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
