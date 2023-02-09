package com.dbd.nanal.handler;

import com.dbd.nanal.dto.PaintingRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileHandler {

    //    public PaintingEntity parseFileInfo(PaintingRequestDTO paintingRequestDTO) throws IOException {
//
//
//        PaintingEntity paintingEntity;
//        MultipartFile multipartFile = paintingRequestDTO.getFile();
//
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String currentDate = now.format(dateTimeFormatter);
//        // 파일 만들기
//        String absolutePath = new File("").getAbsolutePath() + File.separator +File.separator;
//
//        String path = "painting" + File.separator + currentDate;
//        File file = new File(path);
//
//        // 프로젝트 폴더 내에 "painting" 폴더 생성 후 그 안에 현재 날자로 파일 생성
//
//        // 경로가 존재하지 않으면 디렉토리 생성
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        // 저장하기
//        String originalFileExtension = null;
//        String contentType = multipartFile.getContentType();
//        if (!ObjectUtils.isEmpty(contentType)) {
//            if (contentType.contains("image/jpeg")) {
//                originalFileExtension = ".jpg";
//            } else if (contentType.contains("image/png")) {
//                originalFileExtension = ".png";
//            }
//        }
//
//        // 파일 이름이 중복되지 않도록 생성 시간의 나노초까지 구해서 이름으로 사용
//        String newFileName = System.nanoTime() + originalFileExtension;
//        paintingEntity = PaintingEntity.builder().pictureTitle(multipartFile.getOriginalFilename())
//                // photo 파일 아래 날자 파일 아래 파일을 저장
//                .picturePath(path + File.separator + newFileName)
//                .fileSize(multipartFile.getSize())
//                .build();
//
//        file = new File(absolutePath + path + File.separator + newFileName);
//        multipartFile.transferTo(file);
//
//        file.setWritable(true);
//        file.setReadable(true);
//
//        return paintingEntity;
//    }
    public PaintingRequestDTO parseFile(PaintingRequestDTO paintingRequestDTO) throws IOException {

        MultipartFile multipartFile = paintingRequestDTO.getMultipartFile();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = now.format(dateTimeFormatter);
        // 파일 만들기
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

        // [폴더 생성] 프로젝트 폴더 내에 "painting" 폴더 생성 후 그 안에 현재 날자로 파일 생성
        String path = "painting" + File.separator + currentDate;
        File file = new File(path);

        // 경로가 존재하지 않으면 디렉토리 생성
        if (!file.exists()) {
            file.mkdirs();
        }

        // 저장하기
        String originalFileExtension = null;
        String contentType = multipartFile.getContentType();
        if (!ObjectUtils.isEmpty(contentType)) {
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            }
        }

        // 파일 이름이 중복되지 않도록 생성 시간의 나노초까지 구해서 이름으로 사용
        String newFileName = System.nanoTime() + originalFileExtension;
        paintingRequestDTO.setFileSize(multipartFile.getSize());
        paintingRequestDTO.setPictureTitle(multipartFile.getOriginalFilename());

        file = new File(absolutePath + path + File.separator + newFileName);
        multipartFile.transferTo(file);
        file.setWritable(true);
        file.setReadable(true);
        paintingRequestDTO.setFile(file);

        return paintingRequestDTO;
    }

    public File urlToFile(String dalleResult) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = now.format(dateTimeFormatter);

        // [폴더 생성] 프로젝트 폴더 내에 "painting" 폴더 생성 후 그 안에 현재 날자로 파일 생성
        String folderPath = "painting" + File.separator + currentDate;
        File file = new File(folderPath);

        // 경로가 존재하지 않으면 디렉토리 생성
        if (!file.exists()) {
            file.mkdirs();
        }

        // [URL 파일로 변환]
        URL url = new URL(dalleResult);
        String extension = ".png";
        BufferedImage image = ImageIO.read(url);

        String absolutePath = new File("").getAbsolutePath() + File.separator;
        String filePath = "painting" + File.separator + currentDate + File.separator;
        String newFileName = System.nanoTime() + extension;

        file = new File(absolutePath + filePath + newFileName);
        file.setWritable(true);
        file.setReadable(true);
        ImageIO.write(image, "png", file);

        // 파일 만들기

        return file;
    }
}
