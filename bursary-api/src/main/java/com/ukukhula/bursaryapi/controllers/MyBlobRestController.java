package com.ukukhula.bursaryapi.controllers;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ukukhula.bursaryapi.repositories.MyBlobService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blob")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyBlobRestController {
    private final MyBlobService myBlobService;
    static int count = 0;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        MyBlobRestController.count++;
        System.out.println(MyBlobRestController.count);
        try{
            String createUniqueFileName = UUID.randomUUID().toString() +"-" +file.getOriginalFilename();
            String fileUrl = myBlobService.storeFile(createUniqueFileName,file.getInputStream(), file.getSize());

            String responseBody = "{\"url\": \"" + fileUrl + "\"}";
            return ResponseEntity.ok(responseBody);
        }catch (IOException e){
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to upload the file '"+file.getOriginalFilename() +"'" + e.getMessage() + "\"}");
        }
    }
}