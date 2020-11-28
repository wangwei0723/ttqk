package com.haozi.ttqk.controller;

import com.haozi.ttqk.util.FileUploadTool;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {
    @PostMapping("/upload")
    public ResultVo<String> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                           HttpServletRequest request) {
        String filePath = "";
        try {
            String saveFilePath="C:\\Users\\wangwei\\Desktop\\临时文件";
            filePath = FileUploadTool.createFile(multipartFile, saveFilePath);
            log.info("filePath:[{}]",filePath);
            System.out.println("filePath"+filePath);
        } catch (Exception e) {
            log.info("上传文件出现异常",e);
            return ResponseUtil.fail("上传失败");
        }
        if(!StringUtils.isEmpty(filePath)){
            return ResponseUtil.success(filePath);
        }else {
            return ResponseUtil.fail("上传失败");
        }
    }

}
