package com.planeter.review.controller.api;

import com.planeter.review.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * App下载
 */
@RestController
@Slf4j
public class VersionController {
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("src/main/resources/static/"+fileName)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource("src/main/resources/static/"+fileName));
    }

    @GetMapping("/download/list")
    public List<String> getFileList(){
        List<String> list = new ArrayList<>();
        File baseFile = new File("src/main/resources/static");
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                list.add(file.getName());
            }
        }
        return list;
    }
}
