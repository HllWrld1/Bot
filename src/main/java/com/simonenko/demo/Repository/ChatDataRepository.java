package com.simonenko.demo.Repository;

import com.simonenko.demo.Entity.ChatData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDataRepository extends JpaRepository<ChatData, Long> {
}