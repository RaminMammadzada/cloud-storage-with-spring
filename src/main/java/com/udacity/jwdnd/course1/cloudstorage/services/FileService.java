package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    @PostConstruct()
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    public File addItem(MultipartFile multipartFile, Authentication authentication) throws IOException {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        File file = new File();

        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(String.valueOf(multipartFile.getSize()));
        file.setUserId(userId);
        file.setFileData(multipartFile.getBytes());

        fileMapper.insert(file);

        return file;
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteItem(fileId);
    }

    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }
}