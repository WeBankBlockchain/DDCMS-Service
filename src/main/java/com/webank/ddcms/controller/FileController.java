package com.webank.ddcms.controller;

import com.webank.ddcms.service.FileService;
import com.webank.ddcms.vo.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {

  @Autowired private FileService fileService;

  @PostMapping("/upload")
  public CommonResponse handleFileUpload(@RequestParam("file") MultipartFile file)
      throws Exception {
    String filename = fileService.uploadFile(file);
    return CommonResponse.success(filename);
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> handleFileDownload(@RequestParam("filename") String filename)
      throws IOException {
    return fileService.downloadFile(filename);
  }
}
