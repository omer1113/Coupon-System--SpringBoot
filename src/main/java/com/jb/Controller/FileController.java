package com.jb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
//@CrossOrigin("*")
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) throws IOException {
        // String filePath = request.getServletContext().getRealPath("/");
        String filePath =  request.getServletContext().getRealPath("/upload/");
        if(!new File(filePath).exists())
        {
            new File(filePath).mkdir();
        }
        String fileName = file.getOriginalFilename();
        String[] fileSplit = fileName.split("\\.");
        String fileLocation = filePath+ UUID.randomUUID()+"."+fileSplit[1];
        file.transferTo(new File(fileLocation));
        return ResponseEntity.ok(fileLocation);
    }


    @PostMapping("/download")
    public ResponseEntity downloadFile(@RequestParam String fileName) throws IOException {
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }
}