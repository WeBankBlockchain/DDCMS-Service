package com.webank.ddcms.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  String uploadFile(MultipartFile file) throws Exception;

  ResponseEntity<Resource> downloadFile(String filename);
}
