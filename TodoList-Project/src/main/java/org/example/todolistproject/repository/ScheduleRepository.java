package org.example.todolistproject.repository;

import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select new org.example.todolistproject.dto.page.PageResponseDto(" +
            "s.title, " +
            "s.content, " +
            "count(c), " +
            "s.createdAt," +
            "s.modifiedAt, " +
            "u.username) " +
            "from User u " +
            "join Schedule s on u.userId = s.user.userId " +
            "left join Comment c on s.scheduleId = c.schedule.scheduleId " +
            "group by s.scheduleId, s.title, s.content, s.createdAt, s.modifiedAt, u.username " +
            "order by s.modifiedAt desc",
            countQuery = "select count(distinct s.scheduleId) from User u left join Schedule s on u.userId = s.user.userId")
    Page<PageResponseDto> findAllWithComment(Pageable pageable);

    @Query("select s from Schedule s where s.user.userId = :userId")
    List<Schedule> findAllByUserId(@Param("userId") Long userId);
}
