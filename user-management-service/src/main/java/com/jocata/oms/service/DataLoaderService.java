package com.jocata.oms.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataLoaderService {
    void loadData(String filePath);
}
