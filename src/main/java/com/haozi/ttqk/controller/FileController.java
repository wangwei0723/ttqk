package com.haozi.ttqk.controller;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
            @ApiImplicitParam(name = "tiktokUserId", value = "tiktokUserId", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/upload")
    public ResultVo<FileVo> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                           @RequestParam("userId")Integer userId,@RequestParam("tiktokUserId")String tiktokUserId,@RequestParam("fileType")Integer fileType) {
        FileVo fileVo=new FileVo();
        try {
            String saveFileBasePath= SystemConstants.FILE_BASE_PATH;
//            saveFileBasePath="C:\\Users\\wangwei\\Desktop\\临时文件\\";

            String uuid= UUID.randomUUID().toString();
            if(tiktokUserId!=null){
                saveFileBasePath=saveFileBasePath+tiktokUserId+ File.separator+uuid;
            }else {
                saveFileBasePath=saveFileBasePath+uuid;
            }
            FileEntity fileEntity = FileUploadTool.saveFile(multipartFile, saveFileBasePath);
            if(fileEntity==null || StringUtils.isEmpty(fileEntity.getFileName())||StringUtils.isEmpty(fileEntity.getFilePath()) ){
                log.info("文件保存失败");
                return ResponseUtil.fail("文件上传失败");
            }
            String fileUrl=SystemConstants.SERVICE_URL+"file/download/"+uuid;
            TtFile ttFile=new TtFile();
            ttFile.setUuid(uuid);
            ttFile.setFileDirectory(fileEntity.getFilePath());
            ttFile.setFileName(fileEntity.getFileName());
            ttFile.setFileType(fileType);
            ttFile.setTiktokUserId(tiktokUserId);
            ttFile.setUserId(userId);
            ttFile.setFileUrl(fileUrl);
            Integer id=fileService.saveFileInfo(ttFile);
            log.info("filePath:[{}]",fileEntity.getFilePath());
            if(id==null){
                log.info("文件信息保存失败");
                return ResponseUtil.fail("文件上传失败");
            }
            fileVo.setFileUrl(fileUrl);
            if(fileType==2){//视频截取第一帧图片
                String faceImgBasePath= SystemConstants.FILE_BASE_PATH;
//            faceImgBasePath="C:\\Users\\wangwei\\Desktop\\临时文件\\";
                String uuid2= UUID.randomUUID().toString();
                if(tiktokUserId!=null){
                    faceImgBasePath=faceImgBasePath+tiktokUserId+ File.separator+uuid2;
                }else {
                    faceImgBasePath=faceImgBasePath+uuid2;
                }
                String fileName = fileEntity.getFileName().substring(0,fileEntity.getFileName().lastIndexOf("."));
                String faceImgDictory=faceImgBasePath+File.separator+fileName+".jpg";
                FileUploadTool.fetchFrame(fileEntity.getFilePath(),faceImgDictory);
                String faceFileUrl=SystemConstants.SERVICE_URL+"file/download/"+uuid2;
                TtFile ttFaceFile=new TtFile();
                ttFaceFile.setUuid(uuid2);
                ttFaceFile.setFileDirectory(faceImgDictory);
                ttFaceFile.setFileName(fileName+".jpg");
                ttFaceFile.setFileType(1);
                ttFaceFile.setTiktokUserId(tiktokUserId);
                ttFaceFile.setUserId(userId);
                ttFaceFile.setFileUrl(faceFileUrl);
                Integer id2=fileService.saveFileInfo(ttFaceFile);
                log.info("faceFilePath:[{}]",faceImgDictory);
                fileVo.setFileFaceUrl(faceFileUrl);
            }
            log.info("fileUrl:[{}]",fileUrl);
            System.out.println("fileUrl"+fileUrl);
        } catch (Exception e) {
            log.info("上传文件出现异常",e);
            return ResponseUtil.fail("上传失败");
        }
        return ResponseUtil.success(fileVo);
    }

    @ApiOperation(value = "下载文件接口", httpMethod = "GET")
    @RequestMapping("/download/{uuid}")
    public void download(@PathVariable("uuid")String uuid, HttpServletResponse response) {
        FileVo fileVo=new FileVo();
        try {
            if(StringUtils.isEmpty(uuid)){
                log.info("uuid为空");
                response.setHeader("Content-type", "text/html;charset=UTF-8");  //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSONObject.toJSONString(ResponseUtil.fail("uuid不能为空")));
                return ;
            }
            TtFile ttFile=fileService.getFileByUuid(uuid);
            if(ttFile==null){
                log.info("文件不存在");
                response.setHeader("Content-type", "text/html;charset=UTF-8");  //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSONObject.toJSONString(ResponseUtil.fail("文件不存在")));
                return ;
            }

            File file = new File(ttFile.getFileDirectory());
            // 取得文件名。
            String fileName = ttFile.getFileName();
            // 取得文件的后缀名。
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(ttFile.getFileDirectory()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;fileName=\"" + new String(fileName.getBytes("GBK"), "ISO8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            log.info("上传文件出现异常",e);
            return ;
        }
    }




}
