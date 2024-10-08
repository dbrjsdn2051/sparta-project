package org.example.todolistproject.repository;

import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "select " +
            "new org.example.todolistproject.dto.page.PageResponseDto(" +
            "s.title, " +
            "s.content, " +
            "count (c), " +
            "s.createdAt," +
            "s.modifiedAt, " +
            "s.user.username) " +
            "from Schedule s left join s.comments c " +
            "group by s.title, s.content, s.createdAt, s.modifiedAt, s.user.username " +
            "order by s.modifiedAt desc ",
    countQuery = "select count (s) from Schedule s")
    Page<PageResponseDto> findAllWithComment(Pageable pageable);

}
