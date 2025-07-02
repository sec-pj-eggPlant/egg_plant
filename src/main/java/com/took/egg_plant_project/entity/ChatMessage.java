package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage extends BaseTime {

    @Id
    @Column(name = "chatID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roomID",referencedColumnName ="chatRoomID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "senderID",referencedColumnName ="memberID")
    private Member sender;

    @Lob
    private String message;
}

