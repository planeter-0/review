package com.planeter.review.controller.api;

import com.planeter.review.common.enums.ResultCode;
import com.planeter.review.model.vo.ResultVO;
import com.planeter.review.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Planeter
 * @description: ImageController
 * @date 2021/6/11 16:45
 * @status dev
 */
@RestController
@Slf4j
public class ImageController {
    /**
     * 上传图片返回url
     *
     * @param uploadFile
     * @return url
     */
    @PostMapping(value = "/image/upload")
    public ResultVO<String> uploadFile(@RequestParam("image") MultipartFile uploadFile) {
        String url = null;
        try {
            //重命名
            String newFilename = QiniuUtils.renamePic(Objects.requireNonNull(uploadFile.getOriginalFilename()));
            url = QiniuUtils.InputStreamUpload((FileInputStream) uploadFile.getInputStream(), newFilename);
            if (url.contains("error")) {
                return new ResultVO<>(ResultCode.FAILED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("上传图片成功");
        return new ResultVO<>(url);
    }
}
