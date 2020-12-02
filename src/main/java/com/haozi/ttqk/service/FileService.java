package com.haozi.ttqk.service;

import com.haozi.ttqk.model.TtFile;

public interface FileService {
    /**
     * 保存文件信息
     * @param ttFile
     * @return
     */
    Integer saveFileInfo(TtFile ttFile);

    /**
     * 根据uuid查询文件
     * @param uuid
     * @return
     */
    TtFile getFileByUuid(String uuid);


}
