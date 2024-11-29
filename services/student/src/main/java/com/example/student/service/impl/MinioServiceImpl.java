package com.example.student.service.impl;

import com.example.student.repository.MinioRepository;
import com.example.student.service.interfaces.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MinioServiceImpl implements MinioService {

    private final MinioRepository minioRepository;

    @Override
    public void uploadFile(String objectName, String filePath) {
        minioRepository.uploadFile(objectName, filePath);
    }

    @Override
    public String getObjectUrl(String objectName, String bucket) {
        return minioRepository.getObjectUrl(objectName, bucket);
    }
}
