package com.project.web.service.upload;

import com.project.web.exception.Error500Exception;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${image.path}")
    private String updatePath;

    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        long fileSizeInMB = file.getSize() / 1024 / 1024;

        if (fileSizeInMB >= 1) {
            throw new Error500Exception("1MB 미만의 파일만 가능합니다.");
        }


        String uuidStr = UUID.randomUUID().toString();
        File saveFile = new File(updatePath + "/" + uuidStr);
        try {
            file.transferTo(saveFile);
        }
        catch (IllegalStateException e) {
            throw new Error500Exception("파일 업로드에 실패했습니다.");
        }
        catch (IOException e) {
            throw new Error500Exception("파일 업로드에 실패했습니다.");
        }
        return "/uploadImg/" + uuidStr;
    }
}
