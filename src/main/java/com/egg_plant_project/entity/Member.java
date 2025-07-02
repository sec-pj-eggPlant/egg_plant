package com.egg_plant_project.entity;

import com.egg_plant_project.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseTime{

    @Id
    @Column(name = "memberID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String userID;

    @Column(nullable = false, length = 60)
    private String userPW;

    @Column(nullable = false, length = 255, unique = true)
    private String nickName;

    @Column(nullable = false, length = 60)
    private String userName;

    @Column(nullable = false, unique = true, length = 100)
    private String userEmail;

    @Column(nullable = false, unique = true, length = 13)
    private String tel;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_RENTER;
}
