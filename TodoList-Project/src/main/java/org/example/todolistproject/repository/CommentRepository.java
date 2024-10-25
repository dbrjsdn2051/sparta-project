package org.example.todolistproject.repository;

import org.example.todolistproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.schedule.scheduleId = :scheduleId")
    List<Comment> findAllByScheduleId(@Param("scheduleId") Long scheduleId);

    @Modifying
    @Query("delete from Comment c where c.schedule.scheduleId = :scheduleId")
    void deleteByScheduleId(@Param("scheduleId") Long scheduleId);
}

