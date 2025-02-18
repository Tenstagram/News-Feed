package com.example.newsfeed.post.service;

import com.example.newsfeed.post.entity.MediaUrl;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.MediaUrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaUrlService {

    @Autowired
    private final MediaUrlRepository mediaUrlRepository;

    private final String fullPathName = "Media/" ;

    @Transactional
    public String upload(Post post,   MultipartFile  mediaUrl) throws IOException {

        String originalFilename = mediaUrl.getOriginalFilename();//원래 파일명
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;//db 저장용 파일명

        // 파일 저장 경로 지정
        Path filePath = Paths.get(fullPathName + storedFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, mediaUrl.getBytes());

        //파일을 media table에 저장
        MediaUrl MediaUrlEntity = new MediaUrl(originalFilename,storedFilename,filePath.toString(),post);

        MediaUrl saveMediaUrl= mediaUrlRepository.save(MediaUrlEntity);

        return  saveMediaUrl.getMediaUrl();
    }

    public void deleteByPost(Post post){
//        UploadFile uploadFile = fileRepository.findById(fileId)
//                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
        List<MediaUrl> media=new ArrayList<>();
        try {
             media = mediaUrlRepository.findAllByPost(post);
        }catch(Exception ex){
            throw new IllegalArgumentException("해당 이미지를 찾을 수 없습니다.");
        }

        for(MediaUrl m: media) {
            mediaUrlRepository.deleteById(m.getMediaUrlID());
            File file = new File(m.getMediaUrl());
            if (file.exists() && file.delete()) {
                System.out.println("로컬 파일 삭제 성공: " + m.getMediaName());
            } else {
                throw new IllegalArgumentException("해당 이미지를 삭제할 수 없습니다.");
            }
        }
    }

}
