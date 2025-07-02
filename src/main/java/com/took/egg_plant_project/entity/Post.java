package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTime {

    @Id
    @Column(name = "postID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "writerId", nullable = false)
    private Member writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 3500)
    private String content;

    private Integer price;
    private Integer area;

    @Column(length = 200)
    private String location;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 20)
    private String status = "ACTIVE";
}
