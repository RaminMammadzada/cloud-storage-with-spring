package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/fileUpload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            return "home";
        }

        File addedFile = fileService.addItem(file, authentication);
        System.out.printf("The file %s is saved", addedFile.getFileName());

        redirectAttributes.addFlashAttribute("activeTab", "files");
        return "redirect:/home";
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, RedirectAttributes redirectAttributes) {
        this.fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("activeTab", "files");
        return "redirect:/home";
    }

    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<InputStreamSource> viewFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttributes) {
        File file = this.fileService.getFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        MediaType mt = MediaType.parseMediaType(file.getContentType());

        redirectAttributes.addFlashAttribute("activeTab", "files");
        return ResponseEntity.ok().contentType(mt).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"").body(resource);
    }
}
