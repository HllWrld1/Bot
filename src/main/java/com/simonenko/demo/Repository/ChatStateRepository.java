package com.simonenko.demo.Repository;

import com.simonenko.demo.Entity.ChatState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatStateRepository extends JpaRepository<ChatState, Long> {
    @Modifying
    @Query("UPDATE ChatState p SET p.state = :state WHERE p.id = :id")
    @Transactional
    void changeState(@Param("id") long id, @Param("state") String state); //обновляем состояние диалога

    @Query("SELECT state from ChatState WHERE id = :id") //проверяем состояние диалога
    String getChatStateById(@Param("id") long id);
}

