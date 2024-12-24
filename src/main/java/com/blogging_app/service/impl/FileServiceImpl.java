package com.blogging_app.service.impl;

import com.blogging_app.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File Name
        String name = file.getOriginalFilename();

        //random name generate
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filePath = path + File.separator+fileName; //separator is like - /

        //create folder if not created
        File f = new File(path);//file path pe save krenge
        if(!f.exists()){
            f.mkdir();
        }

        //file copy(save)
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        //ToDO; we can write DB logic to return input Stream
        return inputStream;
    }
}
