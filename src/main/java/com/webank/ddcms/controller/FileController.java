package com.webank.ddcms.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.webank.ddcms.enums.CodeEnum;
import com.webank.ddcms.service.FileService;
import com.webank.ddcms.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

  @Autowired private FileService fileService;

  private final Set<String> ALLOWED_FILE_TYPES = new HashSet<String>(){{
    add("png");
    add("jpg");
    add("jpeg");
    add("pdf");
  }};

  @PostMapping("/upload")
  public CommonResponse handleFileUpload(@RequestParam("file") MultipartFile file)
      throws Exception {
    String contentType = file.getContentType();
    String fileName = file.getOriginalFilename();
    if (contentType == null || !contentType.startsWith("image/")) {
      return CommonResponse.error(CodeEnum.PARAMETER_ERROR);
    }
    String ext = null;
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
        ext = fileName.substring(dotIndex + 1).toLowerCase();
    }
    if (Strings.isNullOrEmpty(ext) || !ALLOWED_FILE_TYPES.contains(ext)) {
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
