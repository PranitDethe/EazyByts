package com.PranitDethe.ChatApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PranitDethe.ChatApplication.Model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByTimestampAsc();
    List<Message> findByChatroom_IdOrderByTimestampAsc(Long chatroomId);
}
