package com.took.egg_plant_project.main;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Post getPostById(Integer id) {
        return mainRepository.findById(id).orElse(null);
    }

    public Page<MainDto> filterPostsByConditions(Role role,
                                                 Integer price,
                                                 String location,
                                                 Integer area,
                                                 LocalDate startDate,
                                                 LocalDate endDate,
                                                 String keyword,
                                                 Pageable pageable) {

        List<Post> posts = mainRepository.findAll(); // 추후 QueryDSL로 교체 가능

        List<Post> filtered = posts.stream()
                .filter(p -> p.getWriter().getRole() == role)
                .filter(p -> !"DONE".equals(p.getStatus()))
                .filter(p -> price == null || p.getPrice() <= price)
                .filter(p -> location == null || p.getLocation().contains(location))
                .filter(p -> area == null || p.getArea() <= area)
                .filter(p -> {
                    if (startDate != null && endDate != null) {
                        return !p.getStartDate().isAfter(startDate) && !p.getEndDate().isBefore(endDate);
                    }
                    return true;
                })
                .filter(p -> keyword == null
                        || p.getTitle().contains(keyword)
                        || p.getContent().contains(keyword))
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .toList();

        // ✅ 페이징 처리 (SubList 잘라내기)
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        List<MainDto> pagedDtos = filtered.subList(start, end).stream()
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
                    dto.setWriterRole(post.getWriter().getRole().name());
                    return dto;
                }).toList();

        return new PageImpl<>(pagedDtos, pageable, filtered.size());
    }

    public Page<MainDto> getPagedPosts(Role targetRole, Pageable pageable) {
        return mainRepository.findByWriterRoleAndStatusNotDone(targetRole, pageable)
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
                    dto.setWriterRole(post.getWriter().getRole().name());
                    return dto;
                });
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void savePostWithImage(MainDto dto, Member writer, MultipartFile imageFile) throws IOException {
        String imagePath = "/images/dummy.jpg";

        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.createDirectories(path.getParent()); // 없으면 폴더 생성
            Files.copy(imageFile.getInputStream(), path);
            imagePath = "/upload/" + filename; // 웹 접근 경로
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
                imagePath,
                dto.getLatitude(),
                dto.getLongitude()
        );
        mainRepository.save(post);
    }

    public void updatePostStatus(Post original, String newStatus) {
        Post updated = new Post(
                original.getId(),
                original.getWriter(),
                original.getTitle(),
                original.getContent(),
                original.getPrice(),
                original.getArea(),
                original.getLocation(),
                original.getStartDate(),
                original.getEndDate(),
                newStatus,
                original.getImagePath(),
                original.getLatitude(),
                original.getLongitude()
        );
        mainRepository.save(updated);
    }
}
