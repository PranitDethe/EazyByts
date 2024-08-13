package com.PranitDethe.ChatApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PranitDethe.ChatApplication.Model.Chatroom;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
