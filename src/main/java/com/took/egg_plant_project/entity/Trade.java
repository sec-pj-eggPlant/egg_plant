package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade extends BaseTime {

    @Id
    @Column(name = "tradeID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "renterId")
    private Member renter;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Member owner;

    @Column(length = 20)
    private String status;
}

