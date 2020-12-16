package org.wzp.oauth2.util.fileUpload.util;

import org.springframework.web.multipart.MultipartFile;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.enumeration.ResultCodeEnum;
import org.wzp.oauth2.util.FileUtil;
import org.wzp.oauth2.util.fileUpload.vo.CheckMd5FileVO;
import org.wzp.oauth2.util.fileUpload.vo.FileVO;
import org.wzp.oauth2.util.fileUpload.vo.UploadVO;

import java.io.File;
import java.io.IOException;

/**
 * @Author: wq.li
 * @DATE: 2020/8/7 10:16
 */
public class ChunkUploadUtil {

    private static final String DELIMITER = "-";

    private static String savePath = CustomConfig.fileSave;


    private static String checkFileSavePath() {
        String fileSavePath = savePath + File.separator + "file";
        File file = new File(fileSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileSavePath;
    }


    public static FileVO upload(MultipartFile file, UploadVO uploadVO) throws CustomException {
        Long chunk = uploadVO.getChunk();
        try {
            // 没有分片
            if (chunk == null) {
                return unChunkUpload(file, uploadVO);
            } else {
                return ChunkUpload(file, uploadVO);
            }
        } catch (CustomException e) {
            throw e;
        }
    }


    /**
     * 文件校验
     *
     * @param md5FileVO
     * @return FileVO
     */
    public static FileVO check(CheckMd5FileVO md5FileVO) throws CustomException {
        if (md5FileVO.getType() == null || md5FileVO.getChunk() == null || md5FileVO.getFileMd5() == null || md5FileVO.getSuffix() == null || md5FileVO.getFileName() == null) {
            throw new CustomException(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Integer type = md5FileVO.getType();
        Long chunk = md5FileVO.getChunk();
        String fileName = md5FileVO.getFileMd5() + "." + md5FileVO.getSuffix();
        Long fileSize = md5FileVO.getFileSize();
        String fileSavePath = checkFileSavePath();
        // 未分片校验
        if (type == 0) {
            String destFilePath = fileSavePath + File.separator + fileName;
            File destFile = new File(destFilePath);
            if (destFile.exists() && destFile.length() == fileSize) {
                return new FileVO(fileName, fileSize);
            } else {
                throw new CustomException(ResultCodeEnum.FILE_NOT_EXISTS);
            }
        } else {// 分片校验
            String fileMd5 = md5FileVO.getFileMd5();
            String destFileDir = fileSavePath + File.separator + fileMd5;
            String destFileName = chunk + DELIMITER + fileName;
            String destFilePath = destFileDir + File.separator + destFileName;
            File destFile = new File(destFilePath);
            if (destFile.exists() && destFile.length() == fileSize) {
                throw new CustomException(ResultCodeEnum.CHUNK_EXISTS);
            } else {
                throw new CustomException(ResultCodeEnum.CHUNK_NOT_EXISTS);
            }
        }
    }


    /**
     * 未分片上传
     *
     * @param file
     * @param uploadVO
     * @return FileVO
     */
    public static FileVO unChunkUpload(MultipartFile file, UploadVO uploadVO) throws CustomException {
        String suffix = uploadVO.getSuffix();
        String fileName = uploadVO.getFileMd5() + "." + suffix;
        //判断文件存放目录是否存在
        String fileSavePath = checkFileSavePath();
        // 文件上传
        File destFile = new File(fileSavePath + File.separator + fileName);
        if (file != null && !file.isEmpty()) {
            // 上传目录
            File fileDir = new File(fileSavePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            if (destFile.exists()) {
                destFile.delete();
            }
            try {
                file.transferTo(destFile);
                return new FileVO(fileName);
            } catch (Exception e) {
                throw new CustomException(ResultCodeEnum.FILE_UPLOAD_ERROR);
            }
        }
        throw new CustomException(ResultCodeEnum.UPLOAD_FAIL);
    }


    /**
     * 分片上传
     *
     * @param file
     * @param uploadVO
     * @return FileVO
     */
    public static FileVO ChunkUpload(MultipartFile file, UploadVO uploadVO) throws CustomException {
        String fileMd5 = uploadVO.getFileMd5();
        String fileName = fileMd5 + "." + uploadVO.getSuffix();
        Long chunk = uploadVO.getChunk();// 当前片
        Long chunks = uploadVO.getChunks();// 总共多少片
        //判断文件存放目录是否存在
        String fileSavePath = checkFileSavePath();
        // 分片目录创建
        String chunkDirPath = fileSavePath + File.separator + fileMd5;
        File chunkDir = new File(chunkDirPath);
        if (!chunkDir.exists()) {
            chunkDir.mkdirs();
        }
        // 分片文件上传
        String chunkFileName = chunk + DELIMITER + fileName;
        String chunkFilePath = chunkDir + File.separator + chunkFileName;
        File chunkFile = new File(chunkFilePath);
        try {
            file.transferTo(chunkFile);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.CHUNK_UPLOAD_ERROR);
        }
        // 合并分片
        Long chunkSize = uploadVO.getChunkSize();
        long seek = chunkSize * chunk;
        String destFilePath = fileSavePath + File.separator + fileName;
        File destFile = new File(destFilePath);
        if (chunkFile.length() > 0) {
            try {
                ChunkFileUtil.randomAccessFile(chunkFile, destFile, seek);
            } catch (IOException e) {
                throw new CustomException(ResultCodeEnum.CHUNK_MERGE_FAIL);
            }
        }
        if (chunk == chunks - 1) {
            // 删除分片文件夹
            FileUtil.delFolder(chunkFilePath);
            return new FileVO(fileName);
        } else {
            throw new CustomException(ResultCodeEnum.UPLOADING);
        }
    }


}
