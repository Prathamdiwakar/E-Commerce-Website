package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name of current /orginal file
        String originalFilename = file.getOriginalFilename();

        // generate random uuid to file name
        String randomUUId = UUID.randomUUID().toString();
        String fileName = randomUUId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;

        //check if path exist & create
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
