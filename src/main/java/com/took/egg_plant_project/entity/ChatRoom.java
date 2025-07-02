package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @Column(name = "chatRoomID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "postID",referencedColumnName ="postID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "renter",referencedColumnName ="memberID")
    private Member renter;

    @ManyToOne
    @JoinColumn(name = "owner",referencedColumnName = "memberID")
    private Member owner;
}

