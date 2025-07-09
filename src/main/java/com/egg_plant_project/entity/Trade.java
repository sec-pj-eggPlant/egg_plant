package com.egg_plant_project.entity;

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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "postID")  // ✔️ 정확한 컬럼 지정
    private Post post;

    @ManyToOne
    @JoinColumn(name = "renterID",referencedColumnName = "memberID")
    private Member renter;

    @ManyToOne
    @JoinColumn(name = "ownerID",referencedColumnName ="memberID")
    private Member owner;

    @Column(length = 20)
    private String status;
}

