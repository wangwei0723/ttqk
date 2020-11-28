package com.haozi.ttqk.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
public class FileUploadTool {
    // 文件最大500M
    private static long upload_maxsize = 800 * 1024 * 1024;
    // 文件允许格式
    private static String[] allowFiles = { ".rar", ".doc", ".docx", ".zip",
    ".pdf", ".txt", ".swf", ".xlsx", ".gif", ".png", ".jpg", ".jpeg", 
            ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv", 
            ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb" };
    // 允许转码的视频格式（ffmpeg）
    private static String[] allowFLV = { ".avi", ".mpg", ".wmv", ".3gp",
        ".mov", ".asf", ".asx", ".vob" };
    // 允许的视频转码格式(mencoder)
    private static String[] allowAVI = { ".wmv9", ".rm", ".rmvb" };
        
    public static String createFile(MultipartFile multipartFile, String saveFilePath) {
        boolean bflag = false;
        String fileName = multipartFile.getOriginalFilename();
        // 判断文件不为空
        if (multipartFile.getSize() != 0 && !multipartFile.isEmpty()) {
            bflag = true;
            // 判断文件大小
            if (multipartFile.getSize() <= upload_maxsize) {
                bflag = true;
                // 文件类型判断
                if (checkFileType(fileName)) {
                    bflag = true;
                } else {
                    bflag = false;
                    System.out.println("文件类型不允许");
                }
            } else {
                bflag = false;
                System.out.println("文件大小超范围");
            }
        } else {
            bflag = false;
            System.out.println("文件为空");
        }
        if (bflag) {
            String realPathDir=saveFilePath+File.separator+fileName;
//            String logoPathDir = "/video/";
//            String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
//            // 上传到本地磁盘
//            // String logoRealPathDir = "E:/upload";
            File logoSaveFile = new File(realPathDir);
            if (!logoSaveFile.exists()) {
                logoSaveFile.mkdirs();
            }
//            String name = fileName.substring(0, fileName.lastIndexOf("."));
//            System.out.println("文件名称：" + name);
//            // 新的文件名
//            String newFileName = getName(fileName);
//            // 文件扩展名
//            String fileEnd = this.getFileExt(fileName);
//            // 绝对路径
//            String fileNamedirs = logoRealPathDir + File.separator + newFileName + fileEnd;
            System.out.println("保存的绝对路径：" + realPathDir);
            File filedirs = new File(realPathDir);
            // 转入文件
            try {
                multipartFile.transferTo(filedirs);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return realPathDir;
        } else {
            return null;
        }
    }
        /**
 * 文件类型判断
 *
 * @param fileName
 * @return
 */
        private static  boolean checkFileType(String fileName) {
            Iterator<String> type = Arrays.asList(allowFiles).iterator();
            while (type.hasNext()) {
                String ext = type.next();
                if (fileName.toLowerCase().endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }
        /**
 * 文件大小，返回kb.mb
 *
 * @return
 */
        private String getSize(File file) {
        String size = "";
        long fileLength = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength < 1024) {
        size = df.format((double) fileLength) + "BT";
        } else if (fileLength < 1048576) {
        size = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824) {
        size = df.format((double) fileLength / 1048576) + "MB";
        } else {
        size = df.format((double) fileLength / 1073741824) + "GB";
        }
        return size;
        }
        }