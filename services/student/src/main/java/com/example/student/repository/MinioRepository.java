package com.example.student.repository;

import io.minio.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class MinioRepository {

    private final MinioClient minioClient;
    private static final String STUDENTS_PHOTO_BUCKET = "student";

    public MinioRepository(@Value("${minio.url}") String url,
                           @Value("${minio.access.key}") String accessKey,
                           @Value("${minio.secret.key}") String secretKey) {
        this.minioClient =
                MinioClient.builder()
                        .endpoint(url)
                        .credentials(accessKey, secretKey)
                        .build();
    }

    public void uploadPhoto(String objectName, String filePath) {
        if (!bucketExists(STUDENTS_PHOTO_BUCKET)) {
            createBucket(STUDENTS_PHOTO_BUCKET);
        }
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

    public void downloadPhoto(String objectName, String downloadPath) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(STUDENTS_PHOTO_BUCKET)
                        .object(objectName)
                        .build())) {
            Files.copy(stream, Paths.get(downloadPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
