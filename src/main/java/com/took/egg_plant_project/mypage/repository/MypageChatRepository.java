package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypageChatRepository extends JpaRepository<ChatRoom, Integer> {
    @Query(value = """
        SELECT r.CHATROOMID,
               r.OWNER,
               owner.NICKNAME AS ownerNickname,
               r.RENTER,
               renter.NICKNAME AS renterNickname,
               r.POSTID,
               p.TITLE AS postTitle
          FROM CHAT_ROOM r
          JOIN MEMBER owner ON r.OWNER = owner.MEMBERID
          JOIN MEMBER renter ON r.RENTER = renter.MEMBERID
          JOIN POST p ON r.POSTID = p.POSTID
         WHERE r.OWNER = :userId OR r.RENTER = :userId
         ORDER BY r.CHATROOMID DESC
    """, nativeQuery = true)
    List<Object[]> findMyChatRooms(@Param("userId") Integer userId);
}
