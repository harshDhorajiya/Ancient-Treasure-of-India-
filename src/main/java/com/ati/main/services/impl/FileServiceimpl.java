package com.ati.main.services.impl;

import com.ati.main.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceimpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        //generate file with random name
        String randomID = UUID.randomUUID().toString();
        String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        String filepath = path + File.separator + filename1;

        //create folder if there are no folder with such name
        File f= new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filepath));

         return filename1;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullpath = path + File.separator + filename;
        //to return input steam
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
