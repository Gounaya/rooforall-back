package com.rizomm.m2.rooforall.rooforall.services;

import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.BucketRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.FileRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class MinioService {

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    private MinioClient minioClient;

    final static String bucketUser = "rfa-users";


    public MinioService() throws InvalidPortException, InvalidEndpointException {
        minioClient = new MinioClient(" https://play.min.io", "Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
    }

    public void addImageToProfile(MultipartFile file, String username) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, RegionConflictException {
        User existingUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));

        if (!minioClient.bucketExists(bucketUser)) {
            minioClient.makeBucket(bucketUser);
        }
        minioClient.putObject(bucketUser, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
        existingUser.setPictureURL("https://play.min.io/" +bucketUser + "/" +file.getOriginalFilename());
        userRepository.save(existingUser);
    }

    /*public void createBucket(String bucketName, String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("User does not exist");
        }

        if (minioClient.bucketExists(bucketName)) {
            throw new Exception(bucketName + " already exist. Please choose another bucket name.");
        }

        minioClient.makeBucket(bucketName);
        user.get().getBuckets().add(Bucket.builder()
                .bucketName(bucketName)
                .build());

        userRepository.save(user.get());
    }

    public void uploadFile(String bucketName, MultipartFile file, String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("User does not exist");
        }

        List<Bucket> buckets = user.get().getBuckets();
        List<String> bucketNames = buckets.stream().map(minioConnection -> minioConnection.getBucketName()).collect(Collectors.toList());

        if (bucketNames.contains(bucketName)) {
            minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());

            Bucket bucket = bucketRepository.findByBucketName(bucketName);
            bucket.getFiles().add(File.builder().fileName(file.getOriginalFilename()).build());
            bucketRepository.save(bucket);
        } else {
            throw new Exception("You have no bucket named as " + bucketName);
        }
    }

    public void uploadFiles(MultipartFile[] files, String bucketName, String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("User does not exist");
        }

        List<Bucket> buckets = user.get().getBuckets();
        List<String> bucketNames = buckets.stream().map(minioConnection -> minioConnection.getBucketName()).collect(Collectors.toList());

        if (bucketNames.contains(bucketName)) {
            Bucket bucket = bucketRepository.findByBucketName(bucketName);

            for (MultipartFile file : files) {
                bucket.getFiles().add(File.builder().fileName(file.getOriginalFilename()).build());
                bucketRepository.save(bucket);
                minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
            }
        } else {
            throw new Exception("You have no bucket named as " + bucketName);
        }
    }

    public void downloadFile(String bucketName, Long fileId, String email, HttpServletResponse response) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("User does not exist");
        }

        List<Bucket> buckets = user.get().getBuckets();
        List<String> bucketNames = buckets.stream().map(minioConnection -> minioConnection.getBucketName()).collect(Collectors.toList());

        if (bucketNames.contains(bucketName)) {
            Bucket bucket = bucketRepository.findByBucketName(bucketName);
            List<File> files = bucket.getFiles();
            Optional<File> file = fileRepository.findById(fileId);
            if (file.isPresent()) {
                if (files.contains(file.get())) {
                    InputStream inputStream = minioClient.getObject(bucketName, file.get().getFileName());

                    // Set the content type and attachment header.
                    response.addHeader("Content-disposition", "attachment;filename=" + file.get().getFileName());
                    response.setContentType(URLConnection.guessContentTypeFromName(file.get().getFileName()));

                    // Copy the stream to the response's output stream.
                    IOUtils.copy(inputStream, response.getOutputStream());
                    response.flushBuffer();

                } else {
                    throw new Exception("You have no file in your buckets with that id");
                }
            } else {
                throw new Exception("No file found.");
            }

        } else {
            throw new Exception("You have no bucket named as " + bucketName);
        }

    }

    public void deleteFile(String bucketName, Long fileId, String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("User does not exist");
        }

        List<Bucket> buckets = user.get().getBuckets();
        List<String> bucketNames = buckets.stream().map(minioConnection -> minioConnection.getBucketName()).collect(Collectors.toList());

        if (bucketNames.contains(bucketName)) {
            Bucket bucket = bucketRepository.findByBucketName(bucketName);
            List<File> files = bucket.getFiles();
            Optional<File> file = fileRepository.findById(fileId);
            if (file.isPresent()) {
                if (files.contains(file.get())) {
                    minioClient.removeObject(bucketName, file.get().getFileName());
                    fileRepository.delete(file.get());
                } else {
                    throw new Exception("You have no file in your buckets with that id");
                }
            } else {
                throw new Exception("No file found.");
            }

        } else {
            throw new Exception("You have no bucket named as " + bucketName);
        }

    }*/

}
