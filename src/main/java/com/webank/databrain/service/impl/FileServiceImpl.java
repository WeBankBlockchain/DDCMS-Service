package com.webank.databrain.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.webank.databrain.config.SysConfig;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.exception.DataBrainException;
import com.webank.databrain.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.UUID;


@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private SysConfig sysConfig;

    private File fileUploadFolder;
    @PostConstruct
    private void init(){
        this.fileUploadFolder = new File(sysConfig.getFileConfig().getFileDir());
        this.fileUploadFolder.mkdirs();
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception{
        String originalFileName = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFileName);
        suffix = StrUtil.isBlank(suffix)?"":"."+suffix;
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        File outputFile = new File(this.fileUploadFolder, fileName);

        try(InputStream ins = file.getInputStream(); OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile))){
            IoUtil.copy(ins, os);
        }
        return fileName;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filename) {
        File file = new File(this.fileUploadFolder, filename);
        if(!file.exists() || file.isDirectory()){
            throw new DataBrainException(CodeEnum.FILE_NOT_EXIST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

        // Set the filename header
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
