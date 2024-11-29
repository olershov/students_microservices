package com.example.student.repository;

import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Repository
public class MinioRepository {

    private final MinioClient minioClient;
    public static final String STUDENTS_PHOTO_BUCKET = "student";

    @PostConstruct
    public void init() {
        if (!bucketExists(STUDENTS_PHOTO_BUCKET)) {
            createBucket(STUDENTS_PHOTO_BUCKET);
        }
       var path1 = new File("services/student/src/main/java/com/example/student/photo/123456.jpg").getAbsolutePath();
       var path2 = new File("services/student/src/main/java/com/example/student/photo/345678.jpg").getAbsolutePath();
       var path3 = new File("services/student/src/main/java/com/example/student/photo/654321.jpg").getAbsolutePath();
       uploadFile("123456", path1);
       uploadFile("345678", path2);
       uploadFile("654321", path3);
    }

    public void uploadFile(String objectName, String filePath) {
        Path path = Paths.get(filePath);
        try (InputStream fileStream = Files.newInputStream(path)) {
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(STUDENTS_PHOTO_BUCKET)
                            .object(objectName)
                            .stream(fileStream, Files.size(path), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public String getObjectUrl(String objectName, String bucket) {
        var presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .expiry(3600)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
    }


    @SneakyThrows
    private boolean bucketExists(String bucketName) {
        var args = BucketExistsArgs.builder().bucket(bucketName).build();
        return minioClient.bucketExists(args);
    }

    @SneakyThrows
    private void createBucket(String bucketName) {
        minioClient.makeBucket(
                MakeBucketArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
    }
}
