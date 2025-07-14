package com.took.egg_plant_project.main;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainRepository mainRepository;

    public void savePost(MainDto mainDto, Member writer) {
        Post post = new Post(
                null,
                writer,
                mainDto.getTitle(),
                mainDto.getContent(),
                mainDto.getPrice(),
                mainDto.getArea(),
                mainDto.getLocation(),
                mainDto.getStartDate(),
                mainDto.getEndDate(),
                "거래가능"
        );

        mainRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return mainRepository.findAll();
    }

    public Post getPostById(Integer id) {
        return mainRepository.findById(id).orElse(null);
    }

    public List<Post> getPostByWriterId(Integer writerId) {
        return mainRepository.findByWriterId(writerId);
    }

    public List<Post> getPostByStatus(String status) {
        return mainRepository.findByStatus(status);
    }
}
