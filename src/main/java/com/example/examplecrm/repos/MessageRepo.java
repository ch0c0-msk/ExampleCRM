package com.example.examplecrm.repos;

import com.example.examplecrm.models.Message;
import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.createUser = ?1 order by m.createDate desc")
    Iterable<Message> findByUser(User user);
}
