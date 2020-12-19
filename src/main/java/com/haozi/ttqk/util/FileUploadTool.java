package com.haozi.ttqk.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.haozi.ttqk.vo.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
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
        
    public static FileEntity saveFile(MultipartFile multipartFile, String saveFilePath) {
        boolean bflag = false;
        FileEntity fileEntity=new FileEntity();
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
                    log.info("文件类型不允许");
                    System.out.println("文件类型不允许");
                }
            } else {
                bflag = false;
                log.info("文件大小超范围");
                System.out.println("文件大小超范围");
            }
        } else {
            bflag = false;
            log.info("文件为空");
            System.out.println("文件为空");
        }
        if (bflag) {
            fileEntity.setFileName(fileName);
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
            log.info("保存的绝对路径：" + realPathDir);
            File filedirs = new File(realPathDir);
            // 转入文件
            try {
                multipartFile.transferTo(filedirs);
            } catch (Exception e) {
                log.info("保存文件出现异常",e);
                return null;
            }
            fileEntity.setFilePath(realPathDir);
            return fileEntity;
        } else {
            log.info("保存文件失败");
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

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param videofile  源视频文件路径
     * @param framefile  截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;

        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        int owidth = f.imageWidth ;
        int oheight = f.imageHeight ;
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        Java2DFrameConverter converter =new Java2DFrameConverter();
        BufferedImage fecthedImage =converter.getBufferedImage(f);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
    }
        }