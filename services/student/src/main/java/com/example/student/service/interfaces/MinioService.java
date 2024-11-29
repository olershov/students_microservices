package com.example.student.service.interfaces;

public interface MinioService {

    void uploadFile(String objectName, String filePath);
    String getObjectUrl(String fileName, String bucket);

}
