package com.took.egg_plant_project.main;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainRepository mainRepository;

    public void savePost(MainDto dto, Member writer) {
        Post post = new Post(
                null,
                writer,
                dto.getTitle(),
                dto.getContent(),
                dto.getPrice(),
                dto.getArea(),
                dto.getLocation(),
                dto.getStartDate(),
                dto.getEndDate(),
                "ACTIVE",
                "/images/dummy.jpg"
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

    public List<MainDto> getFilteredPosts(Role targetRole) {
        List<Post> posts = mainRepository.findByRole(targetRole);

        return posts.stream().map(post -> {
            MainDto dto = new MainDto();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setPrice(post.getPrice());
            dto.setArea(post.getArea());
            dto.setLocation(post.getLocation());
            dto.setStartDate(post.getStartDate());
            dto.setEndDate(post.getEndDate());
            dto.setStatus(post.getStatus());
            dto.setImagePath(post.getImagePath());
            return dto;
        }).toList();
    }

    public List<MainDto> filterPostsByConditions(Role role,
                                                 Integer price,
                                                 String location,
                                                 Integer area,
                                                 LocalDate startDate,
                                                 LocalDate endDate,
                                                 String status,
                                                 String keyword) {

        List<Post> posts = mainRepository.findAll(); // 추후 QueryDSL로 교체 가능

        return posts.stream()
                .filter(p -> p.getWriter().getRole() == role)
                .filter(p -> price == null || p.getPrice().equals(price))
                .filter(p -> location == null || p.getLocation().contains(location))
                .filter(p -> area == null || p.getArea() <= area)
                .filter(p -> startDate == null || !p.getStartDate().isBefore(startDate))
                .filter(p -> endDate == null || !p.getEndDate().isAfter(endDate))
                .filter(p -> status == null || p.getStatus().equals(status))
                .filter(p -> keyword == null
                        || p.getTitle().contains(keyword)
                        || p.getContent().contains(keyword))
                .map(post -> {
                    MainDto dto = new MainDto();
                    dto.setId(post.getId());
                    dto.setTitle(post.getTitle());
                    dto.setContent(post.getContent());
                    dto.setPrice(post.getPrice());
                    dto.setArea(post.getArea());
                    dto.setLocation(post.getLocation());
                    dto.setStartDate(post.getStartDate());
                    dto.setEndDate(post.getEndDate());
                    dto.setStatus(post.getStatus());
                    dto.setImagePath(post.getImagePath());
                    return dto;
                }).toList();
    }

    public void savePostWithImage(MainDto dto, Member writer, MultipartFile imageFile) throws IOException {
        String imagePath = "/images/dummy.jpg";
        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/upload/" + filename);
            Files.copy(imageFile.getInputStream(), path);
            imagePath = "/upload/" + filename;
        }

        Post post = new Post(
                null,
                writer,
                dto.getTitle(),
                dto.getContent(),
                dto.getPrice(),
                dto.getArea(),
                dto.getLocation(),
                dto.getStartDate(),
                dto.getEndDate(),
                "ACTIVE",
                imagePath
        );

        mainRepository.save(post);
    }
}
