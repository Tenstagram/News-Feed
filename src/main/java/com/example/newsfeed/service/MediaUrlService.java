package com.example.newsfeed.service;

import com.example.newsfeed.entity.MediaUrl;
import com.example.newsfeed.repository.MediaUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaUrlService {
  //  MediaUrlHandler mediaUrlHandler = new MediaUrlHandler();

    @Autowired
    private final MediaUrlRepository mediaUrlRepository;

    String fullPathName = "C:\\Users\\com\\Desktop\\TeamProject\\News-Feed\\Media\\" ;

    public String upload(MultipartFile MediaUrl) throws IOException {
//        String path = mediaUrlHandler.save(MediaUrl);
//        MediaUrl MediaUrlEntity = new MediaUrl();
//        MediaUrlEntity.setPath(path);
//        mediaUrlRepository.save(MediaUrlEntity);

        String originalFilename = MediaUrl.getOriginalFilename();//원래 파일명
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;//db 저장용 파일명

        // 파일 저장 경로 지정
        Path filePath = Paths.get(fullPathName + storedFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, MediaUrl.getBytes());

     //  파일을 media table에 저장
        MediaUrl MediaUrlEntity = new MediaUrl(originalFilename, storedFilename, filePath.toString());

       MediaUrl saveMediaUrl= mediaUrlRepository.save(MediaUrlEntity);

        return  saveMediaUrl.getMediaUrl();

    }

//    public Optional<String> findOne(){
//        List<MediaUrl> list = mediaUrlRepository.findAll();
//        if (list.isEmpty())
//            return Optional.empty();
//        return Optional.of(list.getFirst().getPath());
//    }
}
