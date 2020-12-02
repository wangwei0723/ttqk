package com.haozi.ttqk.controller;

import com.haozi.ttqk.constant.SystemConstants;
import com.haozi.ttqk.model.TtFile;
import com.haozi.ttqk.service.FileService;
import com.haozi.ttqk.util.FileUploadTool;
import com.haozi.ttqk.util.ResponseUtil;
import com.haozi.ttqk.vo.FileEntity;
import com.haozi.ttqk.vo.FileVo;
import com.haozi.ttqk.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Slf4j
@RequestMapping("/file")
@RestController
@Api(value = "文件管理", description = "文件管理", protocols = "http")
public class FileController {

    @Resource
    private FileService fileService;

    @ApiOperation(value = "上传文件接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型 1、图片 2、视频", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当前操作用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tiktokId", value = "tiktokId", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/upload")
    public ResultVo<FileVo> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                           @RequestParam("userId")Integer userId,@RequestParam("tiktokId")String tiktokId,@RequestParam("fileType")Integer fileType) {
        FileVo fileVo=new FileVo();
        try {
            String saveFileBasePath= SystemConstants.FILE_BASE_PATH;
            String uuid= UUID.randomUUID().toString();
            if(tiktokId!=null){
                saveFileBasePath=saveFileBasePath+tiktokId+ File.separator+uuid;
            }else {
                saveFileBasePath=saveFileBasePath+uuid;
            }
            FileEntity fileEntity = FileUploadTool.saveFile(multipartFile, saveFileBasePath);
            if(fileEntity==null || StringUtils.isEmpty(fileEntity.getFileName())||StringUtils.isEmpty(fileEntity.getFilePath()) ){
                log.info("文件保存失败");
                return ResponseUtil.fail("文件上传失败");
            }
            String fileUrl=SystemConstants.FILE_BASE_PATH+"file/upload"+uuid;
            TtFile ttFile=new TtFile();
            ttFile.setUuid(uuid);
            ttFile.setFileDirectory(fileEntity.getFilePath());
            ttFile.setFileName(fileEntity.getFileName());
            ttFile.setFileType(fileType);
            ttFile.setTiktokId(tiktokId);
            ttFile.setUserId(userId);
            ttFile.setFileUrl(fileUrl);
            Integer id=fileService.saveFileInfo(ttFile);
            log.info("filePath:[{}]",fileEntity.getFilePath());
            if(id==null){
                log.info("文件信息保存失败");
                return ResponseUtil.fail("文件上传失败");
            }

            fileVo.setFileUrl(fileUrl);
            log.info("fileUrl:[{}]",fileUrl);
            System.out.println("fileUrl"+fileUrl);
        } catch (Exception e) {
            log.info("上传文件出现异常",e);
            return ResponseUtil.fail("上传失败");
        }
        return ResponseUtil.success(fileVo);
    }

}
