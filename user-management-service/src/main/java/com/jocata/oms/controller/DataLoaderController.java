package com.jocata.oms.controller;

import com.jocata.oms.service.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataLoaderController {
    @Autowired
    private DataLoaderService dataLoaderService;

    @PostMapping("/load")
    public ResponseEntity<String> loadData(@RequestParam String filePath) {
        dataLoaderService.loadData(filePath);
        return ResponseEntity.ok("Data loaded successfully");
    }
}
