package com.webank.ddcms.controller;

import com.webank.ddcms.enums.CodeEnum;
import com.webank.ddcms.service.FileService;
import com.webank.ddcms.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

  @Autowired private FileService fileService;

  @PostMapping("/upload")
  public CommonResponse handleFileUpload(@RequestParam("file") MultipartFile file)
      throws Exception {
    String contentType = file.getContentType();
    log.info("containt type {}",contentType);
    if (contentType == null || !contentType.startsWith("image/")){
      return CommonResponse.error(CodeEnum.PARAMETER_ERROR);
    }

    String filename = fileService.uploadFile(file);
    return CommonResponse.success(filename);
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> handleFileDownload(@RequestParam("filename") String filename)
      throws IOException {
    return fileService.downloadFile(filename);
  }
}
